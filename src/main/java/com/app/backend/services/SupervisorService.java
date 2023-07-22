package com.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.Supervisor;
import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.repositories.SupervisorRepo;
import com.app.backend.repositories.SupervisorWithPasswordRepo;

@Service
public class SupervisorService {

    @Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
    SupervisorRepo supervisorRepo;
    @Autowired
    SupervisorWithPasswordRepo supervisorWithPasswordRepo;

        public Integer registerSupervisor(SupervisorWithPassword supervisorWithPassword) 
    {
        supervisorWithPassword.setPasswordHash(passwordEncoder.encode(supervisorWithPassword.getPasswordHash()));
        return supervisorWithPasswordRepo.save(supervisorWithPassword).getId();
        
    }

        public List<Supervisor> getSupervisorsByTransporterId(Integer transporterId) {
                return supervisorRepo.findByTransporterId(transporterId);
        }

        public Optional<Supervisor> getSupervisorById(Integer supervisorId) {
            return supervisorRepo.findById(supervisorId);
        }
}
