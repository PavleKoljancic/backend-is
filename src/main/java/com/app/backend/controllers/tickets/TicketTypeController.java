package com.app.backend.controllers.tickets;

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

import com.app.backend.models.tickets.AddTicketTypeRequest;
import com.app.backend.models.tickets.TicketType;
import com.app.backend.models.tickets.TicketsStatistics;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.tickets.TicketTypeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tickets")
public class TicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;

    @GetMapping("/getAllTickets/pagesize={pagesize}size={size}")
    public ResponseEntity<List<TicketType>> getTickets(@PathVariable("pagesize") int page,
            @PathVariable("size") int size) 
            {
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

        return ticketTypeService.addTicketType(request.getTicketType(),request.getTransporterIds(),request.getDocumentTypesIds());

    }

    @GetMapping("/ChangeisActiveTicketTypeId={TicketTypeId}andIsActive={isActive}")
    public boolean changeTicketTypeStatus(@PathVariable("TicketTypeId") Integer TicketTypeId, @PathVariable("isActive") Boolean isActive){

        return ticketTypeService.ChangeIsActiveTicketTypeId(TicketTypeId, isActive);
    }

    @GetMapping("/getTicketsStatistics{days}")
    public ResponseEntity<TicketsStatistics> getTicketsStatistics(@PathVariable("days") Integer days, HttpServletRequest request) {
        
        String role = SecurityUtil.getRoleFromAuthToken(request);
        if("ADMIN".compareTo(role) == 0){

            TicketsStatistics ts = ticketTypeService.getTicketsStatisticsForAdmin(days);
            if(ts != null)
                return ResponseEntity.ok().body(ts);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        else if("SUPERVISOR".compareTo(role) == 0){

            TicketsStatistics ts = ticketTypeService.getTicketsStatisticsForTransporter(days, SecurityUtil.getIdFromAuthToken(request));
            if(ts != null)
                return ResponseEntity.ok().body(ts);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    
}
