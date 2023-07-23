package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend.models.TicketType;



public interface TicketTypeRepo extends PagingAndSortingRepository<TicketType,Integer>  {

    List<TicketType> findByInUseTrue(PageRequest pageRequest);

}
