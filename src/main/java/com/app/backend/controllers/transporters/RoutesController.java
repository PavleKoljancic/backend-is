package com.app.backend.controllers.transporters;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.terminals.RoutesStatistics;
import com.app.backend.models.terminals.RoutesStatisticsRequest;
import com.app.backend.models.terminals.ScanInterraction;
import com.app.backend.models.transporters.Route;
import com.app.backend.repositories.terminals.ScanInterractionRepo;
import com.app.backend.services.terminals.ScanInterractionService;
import com.app.backend.services.transporters.RouteService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    
    @Autowired
    private RouteService routeService;

    @Autowired
    private ScanInterractionService scanInterractionService;

    @PostMapping("/addRoute")
    public ResponseEntity<?> addNewRoute(@RequestBody Route route){

        return ResponseEntity.status(HttpStatus.OK).body(routeService.addNewRoute(route));
    }

    @GetMapping("/getAllRoutesByTransporterId={transporterID}")
    public ResponseEntity<?> getAllRoutesByTransporterId(@PathVariable("transporterID") Integer transporterID){

        return ResponseEntity.status(HttpStatus.OK).body(routeService.getRoutesByTransporterId(transporterID));
    }
    
    @GetMapping("/getAll")    
    public List<Route> getAll() {

        return routeService.getAll();
    }

    @GetMapping("/ChangeisActiveRouteId={RouteId}andIsActive={isActive}")
    public boolean changeSupervisorStatus(@PathVariable("RouteId") Integer RouteId,@PathVariable("isActive") Boolean isActive) {

        return routeService.ChangeIsActiveRouteId(RouteId, isActive);
    }

    @PostMapping("/getRoutesStatistics")
    public ResponseEntity<?> getRoutesStatistics(@RequestBody RoutesStatisticsRequest routesStatisticsRequest) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        try {
            /*Date date1 = dateFormat.parse(routesStatisticsRequest.getTimeFrom());
            Timestamp timeFrom = new Timestamp(date1.getTime());
            Date date2 = dateFormat.parse(routesStatisticsRequest.getTimeUntil());
            Timestamp timeUntil = new Timestamp(date2.getTime());*/
            
            RoutesStatistics rs = scanInterractionService.getRoutesStatistics(routesStatisticsRequest);
            if(rs == null)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
            return ResponseEntity.status(HttpStatus.OK).body(rs);
        } catch (Exception e) {
            return null;
        }

    }
    
}
