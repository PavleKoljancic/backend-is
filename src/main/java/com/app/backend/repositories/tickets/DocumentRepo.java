package com.app.backend.repositories.tickets;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.tickets.Document;
import java.util.List;

public interface DocumentRepo extends JpaRepository<Document,Integer>{
    
     List<Document> findByUserId(Integer userId);
     public List<Document> findBySupervisorIdIsNull(PageRequest pageRequest);
}
