package com.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;


import com.app.backend.models.Supervisor;
import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.models.User;
import com.app.backend.repositories.SupervisorRepo;
import com.app.backend.repositories.SupervisorWithPasswordRepo;

import net.bytebuddy.implementation.bind.annotation.Super;

@Service
public class SupervisorService {

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private SupervisorRepo supervisorRepo;

    @Autowired
    private SupervisorWithPasswordRepo supervisorWithPasswordRepo;

     public Integer registerSupervisor(SupervisorWithPassword supervisorWithPassword) 
    {
        supervisorWithPassword.setPasswordHash(passwordEncoder.encode(supervisorWithPassword.getPasswordHash()));
        return supervisorWithPasswordRepo.save(supervisorWithPassword).getId();
        
    }

    public List<Supervisor> getSupervisorsByTransporterId(Integer transporterId) {
        return supervisorRepo.findByTransporterId(transporterId);
    }

    public Supervisor findByEmail(String email){
        return supervisorRepo.findByEmail(email);
    }

    public boolean ChangeIsActiveSupervisorId(Integer supervisorId, Boolean isActive) {
        
        Optional<Supervisor> result = supervisorRepo.findById(supervisorId);
        if(result.isPresent()&&result.get().getIsActive()!=isActive)
        {
            Supervisor temp = result.get();
            temp.setIsActive(isActive);
            supervisorRepo.save(temp);
            return true;
        }
        return false;
    }

        public List<Supervisor> getAllSupervisors(PageRequest pageRequest) {
            return supervisorRepo.findAll(pageRequest).toList();
        }

        public Supervisor getSupervisorById(Integer Id) {
        Optional<Supervisor> result = supervisorRepo.findById(Id);
        if(result.isPresent())
            return result.get();    
        return null;
    }

    public List<Supervisor> getActiveSupervisors()
    {
        return supervisorRepo.findByisActive(true);
    }

    public List<Supervisor> getInactiveSupervisors()
    {
        return supervisorRepo.findByisActive(false);
    }
}
