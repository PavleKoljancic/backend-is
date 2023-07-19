package com.app.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.backend.repositories.TransactionRepo;



@Service
public class TransactionService {

    TransactionRepo transactionRepo = new TransactionRepo();

}
