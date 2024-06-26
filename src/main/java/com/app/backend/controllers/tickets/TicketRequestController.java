package com.app.backend.controllers.tickets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.tickets.TicketRequest;
import com.app.backend.models.tickets.TicketRequestResponse;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.tickets.TicketRequestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ticketRequests")
public class TicketRequestController {

    @Autowired
    private TicketRequestService ticketRequestService;

    @GetMapping("addTicketRequest={ticketTypeId}&UserId={UserID}&DocumentId={DocumentId}")
    public ResponseEntity<String> addTicketRequest(@PathVariable("ticketTypeId") Integer ticketTypeId, @PathVariable("UserID") Integer userId, @PathVariable("DocumentId") Integer DocumentId,HttpServletRequest request) 
    {     

        Integer id = SecurityUtil.getIdFromAuthToken(request);
        if(DocumentId ==0)
            DocumentId=null;
        if (id == userId)
            return ResponseEntity.ok(ticketRequestService.addTicketRequest(ticketTypeId, userId,DocumentId));
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/getTicketResponseByUserId={userId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketRequestResponse>> getTicketResponseByUserId(
            @PathVariable("userId") Integer userId, @PathVariable("pagesize") Integer page,
            @PathVariable("size") Integer size, HttpServletRequest request) {
        
        String role = SecurityUtil.getRoleFromAuthToken(request);

        if("USER".compareTo(role) == 0){
            Integer id = SecurityUtil.getIdFromAuthToken(request);
            if (id == null || userId != id)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            
        return ResponseEntity.ok(ticketRequestService.getTicketResponsesByUserId(userId,PageRequest.of(page, size)));

    }

    @GetMapping("/getTicketRequestByUserId={userId}/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketRequest>> getTicketRequestByUserId(
            @PathVariable("userId") Integer userId, @PathVariable("pagesize") Integer page,
            @PathVariable("size") Integer size, HttpServletRequest request) {
        
        String role = SecurityUtil.getRoleFromAuthToken(request);

        if("USER".compareTo(role) == 0){
            Integer id = SecurityUtil.getIdFromAuthToken(request);
            if (id == null || userId != id)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        
        return ResponseEntity.ok(ticketRequestService.getTicketRequestByUserId(userId,PageRequest.of(page, size)));

    }
}
