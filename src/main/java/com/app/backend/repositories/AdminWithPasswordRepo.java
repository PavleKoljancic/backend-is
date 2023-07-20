package com.app.backend.repositories;


import org.springframework.data.repository.CrudRepository;

public interface AdminWithPasswordRepo extends CrudRepository<AdminWithPasswordRepo,Integer> {
    
        AdminWithPasswordRepo findByEmail(String email);
}
