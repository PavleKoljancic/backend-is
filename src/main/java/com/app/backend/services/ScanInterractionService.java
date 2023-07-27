package com.app.backend.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.RouteHistory;
import com.app.backend.models.ScanInterraction;
import com.app.backend.repositories.ScanInterractionRepo;

@Service
public class ScanInterractionService {
    
    @Autowired
    private ScanInterractionRepo scanInterractionRepo;

    public List<ScanInterraction> getScanInterractionsForSameRouteByTerminalId(RouteHistory routeHistory, Long minutes){

        Timestamp scanTimeOffset = new Timestamp(System.currentTimeMillis() - minutes * 60 * 1000);

        return scanInterractionRepo.findByIdRouteHistoryTerminalIdEqualsAndIdRouteHistoryRouteIdEqualsAndIdTimeGreaterThanEqual(routeHistory.getPrimaryKey().getTerminalId(), 
        routeHistory.getPrimaryKey().getRouteId(), scanTimeOffset);
    }

    public List<ScanInterraction> getScanInterractionsByTerminalId(Integer terminalId, Long minutes){

        Timestamp scanTimeOffset = new Timestamp(System.currentTimeMillis() - minutes * 60 * 1000);

        return scanInterractionRepo.findByIdRouteHistoryTerminalIdEqualsAndIdTimeGreaterThanEqual(terminalId, scanTimeOffset);
    }
    
}
