package com.app.backend.repositories.tickets;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.tickets.PeriodicTicket;

public interface PeriodicTicketRepo  extends JpaRepository<PeriodicTicket,Integer> {
    
    @Query(
        "select ticket from TICKET_TYPE ticket where ticket.id in (select periodicTicket.Id from PERIODIC_TICKET periodicTicket\n"
         + "inner join ACCEPTED acc on acc.Id.ticketTypeId = periodicTicket.Id where acc.Id.transporterId = :transId)"
    )
    public List<PeriodicTicket> findPeriodicTicketsForTransporter(@Param("transId") Integer transId);
}
