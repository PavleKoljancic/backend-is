package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Supervisor;

public interface SupervisorRepo extends JpaRepository<Supervisor,Integer> {
    
    public List<Supervisor>findByTransporterId(Integer TransporterID);
    public Supervisor findByEmail(String email);
}
