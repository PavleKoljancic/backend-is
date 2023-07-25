package com.app.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Transaction;



public interface TransactionRepo extends JpaRepository<Transaction,Integer> 
{
    
}
