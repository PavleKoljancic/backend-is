package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.Accepted;

public interface AcceptedRepo extends CrudRepository<Accepted,Integer> {
    
}
