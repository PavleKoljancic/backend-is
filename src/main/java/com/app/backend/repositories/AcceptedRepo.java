package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.Accepted;
import com.app.backend.models.AcceptedPrimaryKey;

import java.util.List;


public interface AcceptedRepo extends CrudRepository<Accepted,AcceptedPrimaryKey> {
    
    public List<Accepted> findByIdTransporterId(Integer TransporterId);
}
