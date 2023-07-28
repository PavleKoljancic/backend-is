package com.app.backend.repositories.users;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.users.Supervisor;

public interface SupervisorRepo extends JpaRepository<Supervisor,Integer> {
    
    public List<Supervisor>findByTransporterId(Integer TransporterID);
    public Supervisor findByEmail(String email);
    public List<Supervisor>findByisActive(boolean bool, PageRequest pageRequest);
    public List<Supervisor>findByTransporterIdAndIsActive(Integer TransporterID, Boolean boolean1, PageRequest pageRequest);
}
