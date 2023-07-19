package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.services.TicketRequestsService;



@RestController
@RequestMapping("/api/ticketRequests")
public class TicketRequestsController {
    @Autowired
    TicketRequestsService ticketService;

  
}
