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
    @Query(value = "select * from TRANSACTION  where TRANSACTION.DateAndTime >= :dateTime", nativeQuery = true)
    List<Transaction> findAllWithDateTimeAfter( @Param("dateTime") Timestamp dateTime);
}
