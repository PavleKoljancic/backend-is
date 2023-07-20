package com.app.backend.repositories;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.UserWithPassword;

public interface AdminWithPasswordRepo extends CrudRepository<AdminWithPasswordRepo,Integer> {
    
        AdminWithPasswordRepo findByEmail(String email);
}
