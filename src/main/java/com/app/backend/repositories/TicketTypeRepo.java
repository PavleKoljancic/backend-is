package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.models.TicketType;



public interface TicketTypeRepo extends JpaRepository<TicketType,Integer>  {

    List<TicketType> findByInUseTrue(PageRequest pageRequest);

     
}
