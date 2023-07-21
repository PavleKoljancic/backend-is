package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.TicketController;

public interface TicketControllerRepo extends CrudRepository<TicketController,Integer>{
    
    TicketController findByPin(String pin);
}
