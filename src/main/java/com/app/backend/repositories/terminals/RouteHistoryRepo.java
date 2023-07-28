package com.app.backend.repositories.terminals;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.terminals.RouteHistory;
import com.app.backend.models.terminals.RouteHistoryPrimaryKey;

public interface RouteHistoryRepo extends CrudRepository<RouteHistory,Integer> {
    
    @Query(value = "select * from ROUTE_HISTORY rh where rh.TERMINAL_ID=:terminalId and rh.ToDateTime is null;", nativeQuery = true)
    public RouteHistory findByTerminalIdAndToDateTimeIsNull(@Param("terminalId")Integer terminalId);
    @Query(value = "select * from ROUTE_HISTORY rh where rh.TERMINAL_ID=:terminalId", nativeQuery = true)
    public List<RouteHistory> findByTerminalId(@Param("terminalId")Integer terminalId);
    public RouteHistory findByPrimaryKeyAndToDateTimeIsNull(RouteHistoryPrimaryKey routeHistoryPrimaryKey);
}
