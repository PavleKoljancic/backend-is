package com.app.backend.repositories.tickets;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.tickets.TicketType;



public interface TicketTypeRepo extends JpaRepository<TicketType,Integer>  {

    List<TicketType> findByInUseTrue(PageRequest pageRequest);

     
}
