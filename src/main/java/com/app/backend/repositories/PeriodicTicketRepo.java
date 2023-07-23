package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.PeriodicTicket;

public interface PeriodicTicketRepo extends CrudRepository<PeriodicTicket,Integer> {
    
}
