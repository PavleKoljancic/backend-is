package com.app.backend.repositories.tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.app.backend.models.tickets.AmountTicket;

public interface AmountTicketRepo  extends JpaRepository<AmountTicket,Integer> {
    
    @Query(
        "select ticket from TICKET_TYPE ticket where ticket.id in (select amountTicket.Id from AMOUNT_TICKET amountTicket\n"
         + "inner join ACCEPTED acc on acc.Id.ticketTypeId = amountTicket.Id where acc.Id.transporterId = :transId)"
    )
    public List<AmountTicket> findAmountTicketsForTransporter(@Param("transId") Integer transId);
}
