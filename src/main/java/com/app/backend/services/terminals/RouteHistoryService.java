package com.app.backend.services.terminals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.BackendApplication;
import com.app.backend.models.terminals.RouteHistory;
import com.app.backend.models.terminals.RouteHistoryPrimaryKey;
import com.app.backend.models.terminals.ScanInterraction;
import com.app.backend.models.terminals.ScanInterractionPrimaryKey;
import com.app.backend.models.terminals.Terminal;
import com.app.backend.models.tickets.UserTicket;
import com.app.backend.models.transactions.ScanTransaction;
import com.app.backend.models.transactions.Transaction;
import com.app.backend.models.transporters.Route;
import com.app.backend.models.users.Driver;
import com.app.backend.models.users.User;
import com.app.backend.repositories.terminals.RouteHistoryRepo;
import com.app.backend.repositories.terminals.ScanInterractionRepo;
import com.app.backend.repositories.tickets.AcceptedRepo;
import com.app.backend.repositories.tickets.UserTicketRepo;
import com.app.backend.repositories.transactions.ScanTransactionRepo;
import com.app.backend.repositories.users.UserRepo;
import com.app.backend.services.transactions.TransactionService;
import com.app.backend.services.transporters.RouteService;
import com.app.backend.services.users.DriverService;

import jakarta.transaction.Transactional;

@Service
public class RouteHistoryService {

    @Autowired
    private RouteHistoryRepo routeHistoryRepo;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ScanInterractionService scanInterractionService;

    @Autowired
    private AcceptedRepo acceptedRepo;

    @Autowired
    private UserTicketRepo userTicketRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ScanInterractionRepo scanInterractionRepo;
    @Autowired
    private ScanTransactionRepo scanTransactionRepo;

    public String updateTerminal(Integer terminalId, Integer RouteId, Integer DriverId) {

        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        Optional<Terminal> terminal = terminalService.getById(terminalId);
        Optional<Route> route = routeService.getById(RouteId);

        if (!terminal.isPresent() || !route.isPresent())
            return "TerminalId or RouteId incorrect";

        if (checkTerminalRouteHistory(terminalId) != null)
            return "Terminal je vec aktivan!";

        Driver driver = driverService.getById(DriverId).get();
        if (!driver.getIsActive())
            return "Driver not active anymore";

        Terminal presentTerminal = terminal.get();
        Route presentRoute = route.get();

        if (!presentTerminal.getIsActive())
            return "Terminal not in use";

        if (!presentRoute.getIsActive())
            return "Route not in use";

        if (driver.getTransporterId() != presentTerminal.getTransporterId()
                || driver.getTransporterId() != presentRoute.getTransporterId())
            return "Terminal or route belong to other transporter";

        RouteHistoryPrimaryKey primaryKey = new RouteHistoryPrimaryKey(dateTime, RouteId, terminalId);
        RouteHistory routeHistory = new RouteHistory(primaryKey, DriverId, null);

        if (routeHistoryRepo.save(routeHistory) != null)
            return presentRoute.getName();
        else
            return "Terminal update failed";
    }

    public RouteHistory checkTerminalRouteHistory(Integer terminalId) {

        return routeHistoryRepo.findByTerminalIdAndToDateTimeIsNull(terminalId);
    }

    public boolean closeTerminalRouteHistory(RouteHistory routeHistory) {

        RouteHistory checkedRouteHistory = routeHistoryRepo
                .findByPrimaryKeyAndToDateTimeIsNull(routeHistory.getPrimaryKey());
        if (checkedRouteHistory == null)
            return false;
        else {
            checkedRouteHistory.setToDateTime(new Timestamp(System.currentTimeMillis()));
            routeHistoryRepo.save(checkedRouteHistory);
            return true;
        }
    }

    public List<ScanInterraction> getScanInterractionsForSameRouteByTerminalId(Integer terminalId, Long minutes) {

        RouteHistory routeHistory = checkTerminalRouteHistory(terminalId);

        if (routeHistory == null)
            return null;

        return scanInterractionService.getScanInterractionsForSameRouteByTerminalId(routeHistory, minutes);
    }

    public List<RouteHistory> getRouteHistoriesByTerminalId(Integer terminalId) {

        return routeHistoryRepo.findByTerminalId(terminalId);
    }

    @Transactional
    public String tryScan(Integer terminalId, Integer userId) {
        RouteHistory routeHistory = routeHistoryRepo.findByTerminalIdAndToDateTimeIsNull(terminalId);
        if (routeHistory == null)
            return null;
        Optional<User> reuslt = userRepo.findById(userId);
        if (!reuslt.isPresent())
            return null;
        User user = reuslt.get();
        Integer transporterId = terminalService.getById(terminalId).get().getTransporterId();
        List<Integer> acceptedTicketIds = acceptedRepo.findByIdTransporterId(transporterId).stream().parallel()
                .map(a -> a.getId().getTicketTypeId()).toList();
        List<UserTicket> acceptedUserTickets = userTicketRepo.findByUSERId(userId).stream().parallel()
                .filter(ut -> acceptedTicketIds.contains(ut.getType().getId())).parallel()
                .filter(ut -> (ut.getValidUntilDate() != null
                        && ut.getValidUntilDate().after(new Timestamp(System.currentTimeMillis()))) ||
                        (ut.getUsage() != null && ut.getUsage() > 0))
                .toList();

        Integer transactionId = null;
        String response;

        if (acceptedUserTickets.isEmpty()) {

            BigDecimal scanTicketCost = BackendApplication.scanTicketCost;
            if (user.getCredit().compareTo(scanTicketCost) < 0)
                return null;

            ScanTransaction tempScan = new ScanTransaction();
            tempScan.setAmount(scanTicketCost);
            tempScan.setTerminalId(terminalId);
            tempScan.setUserId(userId);
            tempScan.setTimestamp(new Timestamp(System.currentTimeMillis()));

            transactionId = scanTransactionRepo.save(tempScan).getId();
            user.setCredit(user.getCredit().subtract(scanTicketCost));
            userRepo.save(user);

            response = "Jednokratna karta";

        } else {

            List<UserTicket> periodic = acceptedUserTickets.stream().parallel()
                    .filter(ut -> ut.getValidUntilDate() != null).toList();
            // Amount
            if (periodic.isEmpty()) {
                List<UserTicket> amount = acceptedUserTickets.stream().parallel().filter(ut -> ut.getUsage() != null)
                        .toList();
                if (amount.isEmpty())
                    return null;

                amount.get(0).setUsage(amount.get(0).getUsage() - 1);
                userTicketRepo.save(amount.get(0));

                transactionId = amount.get(0).getTRANSACTION_Id();

                response = amount.get(0).getType().getName();
            } else {

                transactionId = periodic.get(0).getTRANSACTION_Id();

                response = periodic.get(0).getType().getName();
            }

        }
        if (transactionId != null) {
            ScanInterractionPrimaryKey primaryKey = new ScanInterractionPrimaryKey(
                    routeHistory.getPrimaryKey().getFromDateTime(),
                    routeHistory.getPrimaryKey().getRouteId(),
                    routeHistory.getPrimaryKey().getTerminalId(),
                    userId, new Timestamp(System.currentTimeMillis()));

            ScanInterraction scanInterraction = new ScanInterraction(primaryKey, transactionId);
            scanInterractionRepo.save(scanInterraction);
            return response;
        }
        return null;
    }
}
