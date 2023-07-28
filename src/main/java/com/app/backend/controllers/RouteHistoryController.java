package com.app.backend.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.RouteHistory;
import com.app.backend.models.Terminal;
import com.app.backend.services.RouteHistoryService;
import com.app.backend.services.TerminalService;

@RestController
@RequestMapping("/api/routesHistory")
public class RouteHistoryController {
    @Autowired
    private RouteHistoryService routeHistoryService;

    @Autowired
    private TerminalService terminalService;

    @GetMapping("/checkRouteHistoryByTerminalId={TerminalId}")
    public RouteHistory checkTerminalRouteHistory(@PathVariable("TerminalId") Integer terminalId) {

        return routeHistoryService.checkTerminalRouteHistory(terminalId);
    }

    @GetMapping("/GetRouteHistoriesByTerminalId={TerminalId}")
    public ResponseEntity<?> getRouteHistoriesByTerminalId(@PathVariable("TerminalId") Integer TerminalId) {

        Optional<Terminal> result = terminalService.getById(TerminalId);
        if (!result.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(routeHistoryService.getRouteHistoriesByTerminalId(TerminalId));
    }

    @GetMapping("/scanInteractionTerminalId={TerminalId}&UserId={UserId}")
    public ResponseEntity<Boolean> scandInteraction(@PathVariable("TerminalId") Integer terminalId,
            @PathVariable("UserId") Integer userId) 
    {
        return ResponseEntity.ok(false);
    }       
}
