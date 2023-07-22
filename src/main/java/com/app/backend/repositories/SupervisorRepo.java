package com.app.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Supervisor;

public interface SupervisorRepo extends JpaRepository<Supervisor,Integer> {
    
    public List<Supervisor>findByTransporterId(Integer TransporterID);

    public Optional<Supervisor> findById(Integer supervisorId);
}
