package com.app.backend.repositories.users;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.users.TicketController;

public interface TicketControllerRepo extends JpaRepository<TicketController,Integer>{
    
    TicketController findByPin(String pin);
    public List<TicketController> findByIsActiveTrue(PageRequest pageRequest);
    public List<TicketController> findByIsActiveFalse(PageRequest pageRequest);
}
