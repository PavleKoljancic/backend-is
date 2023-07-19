package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.util.Streamable;

import com.app.backend.models.TicketType;



public interface TicketTypeRepo extends PagingAndSortingRepository<TicketType,Integer>  {

    List<TicketType> findByInUseTrue(PageRequest pageRequest);
}
