package com.app.backend.repositories.users;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.users.AdminWithPassword;

public interface AdminWithPasswordRepo extends CrudRepository<AdminWithPassword,Integer> {
    
        AdminWithPassword findByEmail(String email);
}
