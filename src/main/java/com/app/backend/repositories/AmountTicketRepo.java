package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.AmountTicket;

public interface AmountTicketRepo extends CrudRepository<AmountTicket,Integer> {
    
}
