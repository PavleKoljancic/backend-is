package com.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.app.backend.models.TicketRequestResponse;

public interface TicketRequestResponseRepo extends JpaRepository<TicketRequestResponse,Integer>{
    @Procedure(name="processTicketResponse")
    public Boolean processTicketResponse(Integer TicketRequestResponseId);
}
