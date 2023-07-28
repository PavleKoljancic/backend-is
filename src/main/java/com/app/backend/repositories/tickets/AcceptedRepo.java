package com.app.backend.repositories.tickets;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.tickets.Accepted;
import com.app.backend.models.tickets.AcceptedPrimaryKey;

import java.util.List;


public interface AcceptedRepo extends CrudRepository<Accepted,AcceptedPrimaryKey> {
    
    public List<Accepted> findByIdTransporterId(Integer TransporterId);
}
