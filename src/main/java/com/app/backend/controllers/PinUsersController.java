package com.app.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Driver;
import com.app.backend.models.PinUser;
import com.app.backend.models.TicketController;
import com.app.backend.security.JwtGenerator;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.DriverService;
import com.app.backend.services.SupervisorService;
import com.app.backend.services.TicketControllerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/pinusers")
public class PinUsersController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private TicketControllerService ticketControllerService;

    @Autowired
    private AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private SupervisorService supervisorService;

    @PostMapping("/register/driverBySupervisorId={SupervisorId}")
    public ResponseEntity<Integer> register(@RequestBody Driver driver, @PathVariable("SupervisorId") Integer SupervisorId, HttpServletRequest request){

        Integer id = SecurityUtil.getIdFromAuthToken(request);
        if(id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        if(supervisorService.findTransporterId(SupervisorId) != driver.getTransporterId())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(driverService.registerDriver(driver));
    }

    @PostMapping("/register/controller")
    public Integer register(@RequestBody TicketController ticketController){

        return ticketControllerService.registerController(ticketController);
    }
    
    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody PinUser user, HttpServletRequest request) {

        String token = null;
        try{
            AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(request);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getPin(),
                            user.getPin()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String role = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);
            
            Integer id = null;
            boolean isActive = false;

            if("DRIVER".compareTo(role) == 0){
                id = driverService.findByPin(user.getPin()).getId();
                isActive = driverService.findByPin(user.getPin()).getIsActive();
            }
            
            if("CONTROLLER".compareTo(role) == 0){
                id = ticketControllerService.findByPin(user.getPin()).getId();
                isActive = ticketControllerService.findByPin(user.getPin()).getIsActive();
            }
            
            if(isActive)
                token = jwtGenerator.generateToken(authentication, id);
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/drivers/getDriversBySupervisorId={SupervisorId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Driver>> getAllDriversBySupervisorId(@PathVariable("SupervisorId") Integer SupervisorId, @PathVariable("pagesize") int page, 
    @PathVariable("size") int size, HttpServletRequest request){
        
        Integer id = SecurityUtil.getIdFromAuthToken(request);
        
        if(id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        Integer transporterId = supervisorService.findTransporterId(SupervisorId);
        return ResponseEntity.ok().body(driverService.getDriversByTransporterId(transporterId, PageRequest.of(page, size)));
    }

    @GetMapping("/drivers/getActiveDriversBySupervisorId={SupervisorId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Driver>> getActiveDriversBySupervisorId(@PathVariable("SupervisorId")Integer SupervisorId, @PathVariable("pagesize") int page, 
    @PathVariable("size") int size, HttpServletRequest request) {

        Integer id = SecurityUtil.getIdFromAuthToken(request);
        
        if(id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        Integer transporterId = supervisorService.findTransporterId(SupervisorId);
        return ResponseEntity.ok().body(driverService.getActiveDrivers(transporterId, PageRequest.of(page, size)));
    }

    @GetMapping("/drivers/getInactiveDriversBySupervisorId={SupervisorId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<Driver>> getInactiveDriversBySupervisorId(@PathVariable("SupervisorId")Integer SupervisorId, @PathVariable("pagesize") int page, 
    @PathVariable("size") int size, HttpServletRequest request){

        Integer id = SecurityUtil.getIdFromAuthToken(request);
        
        if(id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        Integer transporterId = supervisorService.findTransporterId(SupervisorId);
        return ResponseEntity.ok().body(driverService.getInactiveDrivers(transporterId, PageRequest.of(page, size)));
    }

    @PostMapping("/drivers/ChangeisActiveDriverId={DriverId}andIsActive={isActive}andSupervisorId={SupervisorId}")
    public ResponseEntity<?> changeDriverStatus(@PathVariable("DriverId") Integer DriverId, @PathVariable("isActive") Boolean isActive,
    @PathVariable("SupervisorId") Integer SupervisorId, HttpServletRequest request) {

        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if(id != SupervisorId)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        Integer transporterId = supervisorService.findTransporterId(SupervisorId);
        if(transporterId != null && transporterId == driverService.getTransporterId(DriverId).get().getTransporterId()){

            return ResponseEntity.status(HttpStatus.OK).body(driverService.ChangeIsActiveDriverId(DriverId, isActive));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/controllers/getControllers/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketController>> getAllTicketControllers(@PathVariable("pagesize") int page, @PathVariable("size") int size){
        
        return ResponseEntity.ok().body(ticketControllerService.getAllTicketControllers(PageRequest.of(page, size)));
    }

    @GetMapping("/controllers/getActiveControllers/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketController>> getActiveTicketControllers(@PathVariable("pagesize") int page, @PathVariable("size") int size) {

        return ResponseEntity.ok().body(ticketControllerService.getActiveTicketControllers(PageRequest.of(page, size)));
    }

    @GetMapping("/controllers/getInactiveControllers/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketController>> getInactiveTicketControllers(@PathVariable("pagesize") int page, @PathVariable("size") int size){

        return ResponseEntity.ok().body(ticketControllerService.getInactiveTicketControllers(PageRequest.of(page, size)));
    }

    @PostMapping("/controllers/ChangeisActiveControllerId={ControllerId}andIsActive={isActive}")
    public boolean changeControllerStatus(@PathVariable("ControllerId") Integer ControllerId,@PathVariable("isActive") Boolean isActive) {

        return ticketControllerService.ChangeIsActiveControllerId(ControllerId, isActive);
    }
}
