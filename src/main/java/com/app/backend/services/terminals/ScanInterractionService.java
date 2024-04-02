package com.app.backend.services.terminals;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.terminals.RouteHistory;
import com.app.backend.models.terminals.RoutesFrequencyStatistics;
import com.app.backend.models.terminals.RoutesStatistics;
import com.app.backend.models.terminals.RoutesStatisticsRequest;
import com.app.backend.models.terminals.ScanInterraction;
import com.app.backend.models.terminals.TerminalFrequencyStatistics;
import com.app.backend.repositories.terminals.ScanInterractionRepo;
import com.app.backend.repositories.tickets.TicketTypeRepo;

@Service
public class ScanInterractionService {
    
    @Autowired
    private ScanInterractionRepo scanInterractionRepo;

    @Autowired
    private TicketTypeRepo ticketTypeRepo;

    public List<ScanInterraction> getScanInterractionsForSameRouteByTerminalId(RouteHistory routeHistory, Long minutes){

        Timestamp scanTimeOffset = new Timestamp(System.currentTimeMillis() - minutes * 60 * 1000);

        return scanInterractionRepo.findByIdRouteHistoryTerminalIdEqualsAndIdRouteHistoryRouteIdEqualsAndIdTimeGreaterThanEqual(routeHistory.getPrimaryKey().getTerminalId(), 
        routeHistory.getPrimaryKey().getRouteId(), scanTimeOffset);
    }

    public List<ScanInterraction> getScanInterractionsByTerminalId(Integer terminalId, Long minutes){

        Timestamp scanTimeOffset = new Timestamp(System.currentTimeMillis() - minutes * 60 * 1000);

        return scanInterractionRepo.findByIdRouteHistoryTerminalIdEqualsAndIdTimeGreaterThanEqual(terminalId, scanTimeOffset);
    }
    
    public ScanInterraction addScanInterraction(ScanInterraction scanInterraction) 
    {
        return scanInterractionRepo.save(scanInterraction);
    } 

    public List<ScanInterraction> getScanInterractionsByRouteId(Integer routeId, Timestamp startTime, Timestamp endTime){

        return scanInterractionRepo.findByIdRouteHistoryRouteIdEqualsAndIdTimeGreaterThanEqualAndIdTimeLessThanEqual(routeId, startTime, endTime);
    }

    public HashMap<String, List<Timestamp>> getScansForTerminal(List<ScanInterraction> scanInterractions){

        HashMap<String, List<Timestamp>> scans = new HashMap<>();
        for(ScanInterraction si : scanInterractions){
            String ticket = ticketTypeRepo.findTicketTypeByTransactionId(si.getTransactionId());
            if (ticket != null) {
                if(scans.containsKey(ticket)){
                    scans.get(ticket).add(si.getId().getTime());
                }
                else{
                    List<Timestamp> timestamps = new ArrayList<Timestamp>();
                    timestamps.add(si.getId().getTime());
                    scans.put(ticket, timestamps);
                }
            }
            else{
                if(scans.containsKey("Jednokratna karta")){
                    scans.get("Jednokratna karta").add(si.getId().getTime());
                }
                else{
                    List<Timestamp> timestamps = new ArrayList<Timestamp>();
                    timestamps.add(si.getId().getTime());
                    scans.put("Jednokratna karta", timestamps);
                }
            }
        }

        return scans;
    }

    public List<TerminalFrequencyStatistics> getTerminalFrequencyStatistics(List<ScanInterraction> scanInterractions){

        List<TerminalFrequencyStatistics> terminalFrequencyStatistics = new ArrayList<>();
        if(scanInterractions.size() == 0)
            return terminalFrequencyStatistics;

        Map<Integer, List<ScanInterraction>> groupedByTerminalId = scanInterractions.stream()
                .collect(Collectors.groupingBy(scan -> scan.getId().getRouteHistoryTerminalId()));

        for (List<ScanInterraction> group : groupedByTerminalId.values()) {

            TerminalFrequencyStatistics tfs = new TerminalFrequencyStatistics();
            tfs.setTerminalId(group.get(0).getId().getRouteHistoryTerminalId());
            tfs.setScans(getScansForTerminal(group));

            terminalFrequencyStatistics.add(tfs);
        }

        return terminalFrequencyStatistics;
    }

    public RoutesStatistics getRoutesStatistics(RoutesStatisticsRequest routesStatisticsRequest){

        if(routesStatisticsRequest.getRouteIds().size() == 0)
            return null;
        
        RoutesStatistics rs = new RoutesStatistics();
        for(Integer routeId : routesStatisticsRequest.getRouteIds()){
            RoutesFrequencyStatistics rfs = new RoutesFrequencyStatistics();
            rfs.setRouteId(routeId);
            rfs.setTerminalFrequencyStatistics(getTerminalFrequencyStatistics(getScanInterractionsByRouteId(routeId, routesStatisticsRequest.getTimeFrom(), 
            routesStatisticsRequest.getTimeUntil())));

            rs.getRoutesFrequencyStatistics().add(rfs);
        }

        return rs;
    }

    public List<ScanInterraction> getScandInterractionsByUserId(Integer userId) {
        return scanInterractionRepo.findByIdUserIdEquals(userId);
    }
}
