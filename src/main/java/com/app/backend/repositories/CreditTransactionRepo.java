package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.CreditTransaction;


public interface CreditTransactionRepo  extends JpaRepository<CreditTransaction,Integer>{
        List<CreditTransaction> findBySupervisorId(Integer supervisorId,PageRequest of);
    
}
