package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.Supervisor;
import com.app.backend.models.SupervisorWithPassword;
import com.app.backend.models.UserWithPassword;

public interface SupervisorWithPasswordRepo extends CrudRepository<SupervisorWithPassword,Integer> {
    
        SupervisorWithPassword findByEmail(String email);
}
