package com.app.backend.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.ScanInterraction;

public interface ScanInterractionRepo extends JpaRepository<ScanInterraction, Integer>{

    public List<ScanInterraction> findByIdRouteHistoryTerminalIdEqualsAndIdRouteHistoryRouteIdEquals(Integer TerminalId, Integer RouteId);

    public List<ScanInterraction> findByIdRouteHistoryTerminalIdEqualsAndIdRouteHistoryRouteIdEqualsAndIdTimeGreaterThanEqual(Integer TerminalId, 
    Integer RouteId, Timestamp timeSince);
}
