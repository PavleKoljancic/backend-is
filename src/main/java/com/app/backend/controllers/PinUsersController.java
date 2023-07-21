package com.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Driver;
import com.app.backend.models.PinUser;
import com.app.backend.models.TicketController;
import com.app.backend.security.JwtGenerator;
import com.app.backend.services.DriverService;
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

    @PostMapping("/register/driver")
    public Integer register(@RequestBody Driver driver){
        return driverService.registerDriver(driver);
    }

    @PostMapping("/register/controller")
    public Integer register(@RequestBody TicketController ticketController){
        return ticketControllerService.registerController(ticketController);
    }
    
    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody PinUser user, HttpServletRequest request) {

        AuthenticationManager authenticationManager = authenticationManagerResolver.resolve(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getPin(),
                        user.getPin()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
