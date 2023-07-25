package com.app.backend.controllers;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.Transaction;
import com.app.backend.services.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/getTransactionsAfter{TimeStamp}")
    public List<Transaction> getTransactionsAfter(@PathVariable("TimeStamp") Timestamp timestamp) 
    {
        return transactionService.findAllWithDateTimeAfter(timestamp);
    }
}
