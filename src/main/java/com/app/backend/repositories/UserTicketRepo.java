package com.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.UserTicket;
import java.util.List;


public interface UserTicketRepo extends CrudRepository<UserTicket,Integer> {
    
    public List<UserTicket> findByUSERId(Integer USER_Id);
}
