package com.app.backend.services;


import org.springframework.stereotype.Service;

import com.app.backend.repositories.TransactionRepo;



@Service
public class TransactionService {

    TransactionRepo transactionRepo = new TransactionRepo();

}
