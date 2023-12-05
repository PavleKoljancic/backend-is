package com.app.backend;

import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.backend.services.transactions.TransactionService;

@SpringBootApplication
public class BackendApplication {

	public static BigDecimal scanTicketCost = new BigDecimal(0);
	public static void main(String[] args) {

		scanTicketCost = TransactionService.getScanTransactionAmount();
		SpringApplication.run(BackendApplication.class, args);
	}

}
