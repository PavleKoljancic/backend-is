package com.app.backend.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.Driver;
import com.app.backend.models.Route;
import com.app.backend.models.RouteHistory;
import com.app.backend.models.RouteHistoryPrimaryKey;
import com.app.backend.models.Terminal;
import com.app.backend.repositories.RouteHistoryRepo;

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

    public String updateTerminal(Integer terminalId, Integer RouteId, Integer DriverId){
        //Ucitati i vozaca i provjeriti da i terminal i ruta pripadaju njegovom transporteru
        
        Date dateTime = new Date(System.currentTimeMillis());
        
        Driver driver = driverService.getById(DriverId).get();
        if(!driver.getIsActive())
            return "Driver not active anymore";

        Optional<Terminal> terminal = terminalService.getById(terminalId);
        Optional<Route> route = routeService.getById(RouteId);

        if(!terminal.isPresent() || !route.isPresent())
            return "TerminalId or RouteId incorrect";
        
        Terminal presentTerminal = terminal.get();
        Route presentRoute = route.get();

        if(!presentTerminal.getIsActive())
            return "Terminal not in use";

        if(!presentRoute.getIsActive())
            return "Route not in use";

        if(driver.getTransporterId() != presentTerminal.getTransporterId() || driver.getTransporterId() != presentRoute.getTransporterId())
            return "Terminal or route belong to other transporter";
        
        RouteHistoryPrimaryKey primaryKey = new RouteHistoryPrimaryKey(dateTime, RouteId, terminalId);
        RouteHistory routeHistory = new RouteHistory(primaryKey, DriverId);

        if(routeHistoryRepo.save(routeHistory) != null)
            return presentRoute.getName();
        else    
            return "Terminal update failed";
    }
}
