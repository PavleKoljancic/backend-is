package com.app.backend.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.Transaction;



public interface TransactionRepo extends JpaRepository<Transaction,Integer> 
{

    
    List<Transaction> findByTimestampGreaterThan(Timestamp dateAndTime);
    List<Transaction> findByTimestampGreaterThan(Timestamp dateAndTime, PageRequest pageRequest);
    List<Transaction> findByTimestampGreaterThanEqualAndTimestampLessThanEqual(Timestamp start, Timestamp end,PageRequest page);
    List<Transaction> findByUserId(Integer userId,PageRequest pageRequest);
}
