package com.app.backend.services;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.CreditTransaction;
import com.app.backend.models.Transaction;
import com.app.backend.repositories.CreditTransactionRepo;
import com.app.backend.repositories.TransactionRepo;



@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    CreditTransactionRepo creditTransactionRepo;

    public List<Transaction> findAllWithDateTimeAfter(Timestamp dateTime) 
    {
        return transactionRepo.findByTimestampGreaterThan(dateTime);
    }
        public List<Transaction> findAllWithDateTimeAfter(Timestamp dateTime,PageRequest pageRequest) 
    {
        return transactionRepo.findByTimestampGreaterThan(dateTime,pageRequest);
    }
    public List<Transaction> findInBetween(Timestamp start, Timestamp end, PageRequest pageRequest) 
    {
        if(start.after(end))
        return null;
        return transactionRepo.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(start, end,pageRequest);
    }

    public List<Transaction> findUserTransactions(Integer UserId,PageRequest pageRequest) 
    {
        return transactionRepo.findByUserId(UserId, pageRequest);
    }
    public List<CreditTransaction> findSupervisorTransactions(Integer supervisorId, PageRequest of) {
        return creditTransactionRepo.findBySupervisorId(supervisorId, of);
    }
}
