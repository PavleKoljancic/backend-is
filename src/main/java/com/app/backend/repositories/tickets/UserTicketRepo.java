package com.app.backend.repositories.tickets;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.tickets.UserTicket;

import java.util.List;


public interface UserTicketRepo extends CrudRepository<UserTicket,Integer> {
    
    public List<UserTicket> findByUSERId(Integer USER_Id);
}
