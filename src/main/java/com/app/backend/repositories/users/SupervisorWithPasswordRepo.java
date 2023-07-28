package com.app.backend.repositories.users;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.users.SupervisorWithPassword;


public interface SupervisorWithPasswordRepo extends CrudRepository<SupervisorWithPassword,Integer> {
    
        SupervisorWithPassword findByEmail(String email);
}
