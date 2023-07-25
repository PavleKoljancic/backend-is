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

import com.app.backend.models.AddTicketTypeRequest;
import com.app.backend.models.TicketType;
import com.app.backend.services.TicketTypeService;

@RestController
@RequestMapping("/api/tickets")
public class TicketTypeController {

    @Autowired
    TicketTypeService ticketTypeService;

    @GetMapping("/getAllTickets/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketType>> getTickets(@PathVariable("pagesize") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.ok().body(ticketTypeService.getTickets(PageRequest.of(page, size)));
    }

    @GetMapping("/getInUseTickets/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketType>> getTicketsInUse(@PathVariable("pagesize") int page,
            @PathVariable("size") int size) {
        return ResponseEntity.ok().body(ticketTypeService.getAvailableTickets(PageRequest.of(page, size)));
    }

    @GetMapping("/getInUseTicketsForTransporter/{TransporterId}")
    public ResponseEntity<List<TicketType>> getTicketsInUseForTransporter( @PathVariable("TransporterId") Integer TransporterId) {
        return ResponseEntity.ok().body(ticketTypeService.getAvailableTicketsForTransporter(TransporterId));
    }


    @PostMapping("/addTicketType")
    public Boolean addTicketType(@RequestBody AddTicketTypeRequest request) {

        return ticketTypeService.addTicketType(request.getTicketType(),request.getTransporterIds());

    }
}
