package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Route;
import com.app.backend.models.RouteHistory;
import com.app.backend.services.RouteHistoryService;
import com.app.backend.services.RouteService;

@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    
    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteHistoryService routeHistoryService;

    @PostMapping("/addRoute")
    public ResponseEntity<?> addNewRoute(@RequestBody Route route){

        return ResponseEntity.status(HttpStatus.OK).body(routeService.addNewRoute(route));
    }

    @GetMapping("/getAllRoutesByTransporterdId={transporterID}")
    public ResponseEntity<?> getAllRoutesByTransporterId(@PathVariable("transporterID") Integer transporterID){

        return ResponseEntity.status(HttpStatus.OK).body(routeService.getRoutesByTransporterId(transporterID));
    }
    
    @GetMapping("/getAll")    
    public List<Route> getAll() {

        return routeService.getAll();
    }

    @PostMapping("/ChangeisActiveRouteId={RouteId}andIsActive={isActive}")
    public boolean changeSupervisorStatus(@PathVariable("RouteId") Integer RouteId,@PathVariable("isActive") Boolean isActive) {

        return routeService.ChangeIsActiveRouteId(RouteId, isActive);
    }

    @GetMapping("/checkRouteHistoryByTerminalId={TerminalId}")
    public RouteHistory checkTerminalRouteHistory(@PathVariable("TerminalId") Integer terminalId){

        return routeHistoryService.checkTerminalRouteHistory(terminalId);
    }
}
