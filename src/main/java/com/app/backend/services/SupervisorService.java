package com.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.models.UserWithPassword;
import com.app.backend.repositories.SupervisorWithPasswordRepo;

@Service
public class SupervisorService {

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    SupervisorWithPasswordRepo supervisorWithPasswordRepo;

        public Integer registerSupervisor(SupervisorWithPassword supervisorWithPassword) 
    {
        supervisorWithPassword.setPasswordHash(passwordEncoder.encode(supervisorWithPassword.getPasswordHash()));
        return supervisorWithPasswordRepo.save(supervisorWithPassword).getId();
        
    }
}
