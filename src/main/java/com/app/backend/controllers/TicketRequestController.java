package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.TicketRequest;
import com.app.backend.models.TicketRequestResponse;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.SupervisorService;
import com.app.backend.services.TicketRequestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ticketRequests")
public class TicketRequestController {

    @Autowired
    private TicketRequestService ticketRequestService;

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("addTicketRequest={ticketTypeId}&UserId={UserID}")
    public ResponseEntity<String> addTicketRequest(@PathVariable("ticketTypeId") Integer ticketTypeId, @PathVariable("UserID") Integer userId, HttpServletRequest request) 
    {
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if (id == userId)
            return ResponseEntity.ok(ticketRequestService.addTicketRequest(ticketTypeId, userId));
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("getTicketRequests&SupervisorId={SupervisorId}")
    public ResponseEntity<List<TicketRequest>> getTicketRequests(@PathVariable("SupervisorId") Integer SupervisorId) {

        return ResponseEntity.ok(ticketRequestService.getTicketRequestByTransporterId(
                supervisorService.findTransporterId(SupervisorId), PageRequest.of(0, 50)));

    }

    @PostMapping("/addTicketResponse")
    public ResponseEntity<String> addTicketResponse(@RequestBody TicketRequestResponse response,
            HttpServletRequest request) {
        
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if (id == null || response.getSupervisorId() != id)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        return ResponseEntity.ok(ticketRequestService.addTicketResponse(response));

    }

    @GetMapping("/getTicketResponseBySupervisorId={supervisorId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketRequestResponse>> getTicketResponse(
            @PathVariable("supervisorId") Integer supervisorId, @PathVariable("pagesize") Integer page,
            @PathVariable("size") Integer size, HttpServletRequest request) {
        
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if (id == null || supervisorId != id)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            
        return ResponseEntity.ok(ticketRequestService.getTicketResponses(supervisorId,PageRequest.of(page, size)));

    }
    @GetMapping("/getTicketResponseByUserId={userId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketRequestResponse>> getTicketResponseByUserId(
            @PathVariable("userId") Integer userId, @PathVariable("pagesize") Integer page,
            @PathVariable("size") Integer size, HttpServletRequest request) {
        
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if (id == null || userId != id)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            
        return ResponseEntity.ok(ticketRequestService.getTicketResponsesByUserId(userId,PageRequest.of(page, size)));

    }

        @GetMapping("/getTicketRequestByUserId={userId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketRequest>> getTicketRequestByUserId(
            @PathVariable("userId") Integer userId, @PathVariable("pagesize") Integer page,
            @PathVariable("size") Integer size, HttpServletRequest request) {
        
        Integer id = SecurityUtil.getIdFromAuthToken(request);

        if (id == null || userId != id)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            
        return ResponseEntity.ok(ticketRequestService.getTicketRequestByUserId(userId,PageRequest.of(page, size)));

    }
}
