package com.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.services.TerminalService;



@RestController
@RequestMapping("/terminal/ActivationRequests")
public class TerminalController {
    
    @Autowired
    TerminalService terminalService;


}
