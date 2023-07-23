package com.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.services.TicketRequestService;

@RestController
@RequestMapping("/api/ticketRequests")
public class TicketRequestController {
    @Autowired
    TicketRequestService ticketRequestService;

    @GetMapping("addTicketRequest={ticketTypeId}&UserId={UserID}")
    public ResponseEntity<String> addTicketRequest(@PathVariable("ticketTypeId") Integer ticketTypeId, @PathVariable("UserID") Integer userId) 
    {
        return ResponseEntity.ok(ticketRequestService.addTicketRequest(ticketTypeId, userId));

    }
}
