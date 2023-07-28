package com.app.backend.repositories.tickets;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.tickets.TicketRequestResponse;

import java.util.List;


public interface TicketRequestResponseRepo extends JpaRepository<TicketRequestResponse,Integer>{
    @Procedure(name="processTicketResponse")
    public Boolean processTicketResponse(Integer TicketRequestResponseId);

    public List<TicketRequestResponse> findBySupervisorId(Integer supervisorId, PageRequest pageRequest);

    @Query("select ticketResponse from TICKET_REQUEST_RESPONSE ticketResponse inner join TICKET_REQUEST tr on tr.Id=ticketResponse.ticketRequestId where tr.userId=:userId")
    public List<TicketRequestResponse> findByUserId(@Param("userId")Integer userId, PageRequest of);
}
