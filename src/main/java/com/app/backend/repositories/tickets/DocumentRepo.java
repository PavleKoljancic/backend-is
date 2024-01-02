package com.app.backend.repositories.tickets;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.tickets.Document;

public interface DocumentRepo extends JpaRepository<Document,Integer>{
    
}
