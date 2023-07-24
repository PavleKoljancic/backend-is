package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.TicketRequest;
import com.app.backend.models.TicketRequestResponse;
import com.app.backend.repositories.TicketRequestRepo;
import com.app.backend.services.SupervisorService;
import com.app.backend.services.TicketRequestService;

@RestController
@RequestMapping("/api/ticketRequests")
public class TicketRequestController {
    @Autowired
    TicketRequestService ticketRequestService;
    @Autowired
    SupervisorService supervisorService;
    @GetMapping("addTicketRequest={ticketTypeId}&UserId={UserID}")
    public ResponseEntity<String> addTicketRequest(@PathVariable("ticketTypeId") Integer ticketTypeId, @PathVariable("UserID") Integer userId) 
    {
        return ResponseEntity.ok(ticketRequestService.addTicketRequest(ticketTypeId, userId));

    }
    @GetMapping("getTicketRequests&SupervisorId={SupervisorId}")
    public ResponseEntity<List<TicketRequest>> getTicketRequests(@PathVariable("SupervisorId")Integer SupervisorId) 
    {   

        return ResponseEntity.ok(ticketRequestService.getTicketRequestByTransporterId(supervisorService.findTransporterId(SupervisorId), PageRequest.of(0,50) ));

    }
    @PostMapping("/addTicketResponse")
    public ResponseEntity<String> addTicketResponse(@RequestBody TicketRequestResponse response) 
    {
        return ResponseEntity.ok(ticketRequestService.addTicketResponse(response));

    }
}
