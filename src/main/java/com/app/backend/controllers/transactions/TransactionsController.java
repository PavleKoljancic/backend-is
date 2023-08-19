package com.app.backend.controllers.transactions;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.transactions.CreditTransaction;
import com.app.backend.models.transactions.ScanTransaction;
import com.app.backend.models.transactions.Transaction;
import com.app.backend.services.transactions.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getTransactionsAfterAll{TimeStamp}")
    public List<Transaction> getTransactionsAfter(@PathVariable("TimeStamp") Timestamp timestamp) {
        return transactionService.findAllWithDateTimeAfter(timestamp);
    }

    @GetMapping("/getTransactionsAfterPaged{TimeStamp}page={page}size={size}")
    public List<Transaction> getTransactionsAfterPaged(@PathVariable("TimeStamp") Timestamp timestamp,
            @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return transactionService.findAllWithDateTimeAfter(timestamp, PageRequest.of(page, size));
    }

    @GetMapping("/getTransactionsInBetweenStart={start}End={end}page={page}size={size}")
    public List<Transaction> getTransactionsBetween(@PathVariable("start") Timestamp start,
            @PathVariable("end") Timestamp end, @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {
        return transactionService.findInBetween(start, end, PageRequest.of(page, size));
    }

    @GetMapping("/getTransactionsForUser={UserId}page={page}size={size}")
    public List<Transaction> geTransactionsByUserID(@PathVariable("UserId") Integer UserId,
            @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return transactionService.findUserTransactions(UserId, PageRequest.of(page, size));
    }

    @GetMapping("/getCreditTransactionsBySupervisorId={SupervisorId}page={page}size={size}")
    public List<CreditTransaction> geTransactionsBySupervisor(@PathVariable("SupervisorId") Integer SupervisorId,
            @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return transactionService.findSupervisorTransactions(SupervisorId, PageRequest.of(page, size));
    }

    @GetMapping("/getScanTransactionsByTerminalId={TerminalId}page={page}size={size}")
    public List<ScanTransaction> geTransactionsByTerminal(@PathVariable("TerminalId") Integer TerminalId,
            @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return transactionService.findTransactionsByTerminalId(TerminalId, PageRequest.of(page, size));
    }

    @GetMapping("/getTransactionsByTransporterId{transporterId}/page={page}size={size}")
    public List<Transaction> geTransactionsByTransporterId( @PathVariable("transporterId") Integer transporterId,@PathVariable("page") Integer page, 
    @PathVariable("size") Integer size) 
    {
        return transactionService.getTransactionsTransporterIdPaged(transporterId, PageRequest.of(page, size, Sort.Direction.DESC, "Id"));
    }
}