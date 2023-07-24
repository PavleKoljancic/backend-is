package com.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.app.backend.models.TicketRequest;

public interface TicketRequestRepo extends JpaRepository<TicketRequest,Integer> {
    @Procedure(name="addTicketRequest")
    public Integer addTicketRequest(Integer pUserId,Integer pTicketTypeId);

}
