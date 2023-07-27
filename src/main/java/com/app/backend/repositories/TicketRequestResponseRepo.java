package com.app.backend.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.app.backend.models.TicketRequestResponse;
import java.util.List;


public interface TicketRequestResponseRepo extends JpaRepository<TicketRequestResponse,Integer>{
    @Procedure(name="processTicketResponse")
    public Boolean processTicketResponse(Integer TicketRequestResponseId);

    public List<TicketRequestResponse> findBySupervisorId(Integer supervisorId, PageRequest pageRequest);
}
