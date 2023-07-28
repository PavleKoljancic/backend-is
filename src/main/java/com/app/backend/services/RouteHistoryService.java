package com.app.backend.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.Driver;
import com.app.backend.models.Route;
import com.app.backend.models.RouteHistory;
import com.app.backend.models.RouteHistoryPrimaryKey;
import com.app.backend.models.ScanInterraction;
import com.app.backend.models.ScanInterractionPrimaryKey;
import com.app.backend.models.Terminal;
import com.app.backend.models.User;
import com.app.backend.models.UserTicket;
import com.app.backend.repositories.AcceptedRepo;
import com.app.backend.repositories.RouteHistoryRepo;
import com.app.backend.repositories.UserRepo;
import com.app.backend.repositories.UserTicketRepo;

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
    private TransactionService transactionService;

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
    public ScanInterraction tryScan(Integer terminalId, Integer userId) {
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
               ScanInterraction scanInterraction = new ScanInterraction(
                        new ScanInterractionPrimaryKey(routeHistory.getPrimaryKey().getFromDateTime(), userId,
                                terminalId,
                                userId, new Timestamp(System.currentTimeMillis())));        
        if (acceptedUserTickets.isEmpty()) { // Ovdje nema ni jedan ispravan ticket koji transporter prihvaca
            if (user.getCredit().compareTo(new BigDecimal(2.8)) < 0)
                return null;
            if (transactionService.addScanTransaction(new BigDecimal(2.8), userId, terminalId) == 1) 
         
                return scanInterractionService.addScanInterraction(scanInterraction);

            return null;

        }
        else 
        {

            List<UserTicket> periodic  = acceptedUserTickets.stream().parallel().filter(ut -> ut.getValidUntilDate()!=null).toList();
            if(periodic.isEmpty())
            {
                List<UserTicket> amount = acceptedUserTickets.stream().parallel().filter(ut-> ut.getUsage()!=null).toList();
                if(amount.isEmpty())
                    return null;
                UserTicket temp = amount.get(0);
                temp.setUsage(temp.getUsage()-1);
                userTicketRepo.save(temp);
                return scanInterractionService.addScanInterraction(scanInterraction);
            }
            else 
            {
                return scanInterractionService.addScanInterraction(scanInterraction);
            }

        }



    }
}
