package com.app.backend.repositories;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.AdminWithPassword;

public interface AdminWithPasswordRepo extends CrudRepository<AdminWithPassword,Integer> {
    
        AdminWithPassword findByEmail(String email);
}
