package com.app.backend.repositories.transactions;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.transactions.CreditTransaction;


public interface CreditTransactionRepo  extends JpaRepository<CreditTransaction,Integer>{
        List<CreditTransaction> findBySupervisorId(Integer supervisorId,PageRequest of);
    
}
