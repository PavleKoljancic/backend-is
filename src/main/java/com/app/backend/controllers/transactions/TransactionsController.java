package com.app.backend.controllers.transactions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.BackendApplication;
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
    public List<Transaction> getTransactionsAfter(@PathVariable("TimeStamp") String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateString);
            Timestamp timestamp = new Timestamp(date.getTime());
            return transactionService.findAllWithDateTimeAfter(timestamp);
        } catch (ParseException e) {
            return null;
        }
    }

    @GetMapping("/getTransactionsAfterPaged{TimeStamp}page={page}size={size}")
    public List<Transaction> getTransactionsAfterPaged(@PathVariable("TimeStamp") String dateString,
            @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateString);
            Timestamp timestamp = new Timestamp(date.getTime());
            return transactionService.findAllWithDateTimeAfter(timestamp, PageRequest.of(page, size));
        } catch (ParseException e) {
            return null;
        }
    }

    @GetMapping("/getTransactionsInBetweenStart={start}End={end}page={page}size={size}")
    public List<Transaction> getTransactionsBetween(@PathVariable("start") String start,
            @PathVariable("end") String end, @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        try {
            Date dateStart = dateFormat.parse(start);
            Timestamp timestampStart = new Timestamp(dateStart.getTime());
            Date dateEnd = dateFormat.parse(end);
            Timestamp timestampEnd = new Timestamp(dateEnd.getTime());
            return transactionService.findInBetween(timestampStart, timestampEnd, PageRequest.of(page, size));
        } catch (ParseException e) {
            return null;
        }
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

    @GetMapping("/getTransactionsByTransporterId={transporterId}page={page}size={size}")
    public List<Transaction> geTransactionsByTransporterId( @PathVariable("transporterId") Integer transporterId,@PathVariable("page") Integer page, 
    @PathVariable("size") Integer size) 
    {
        return transactionService.getTransactionsTransporterIdPaged(transporterId, PageRequest.of(page, size, Sort.Direction.DESC, "Id"));
    }

    @PostMapping("/setScanTransactionAmount={amount}")
    public ResponseEntity<?> setScanTransactionAmount(@PathVariable("amount") BigDecimal newAmount){
        
        if(transactionService.setScanTransactionAmount(newAmount))
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }

    @GetMapping("/getScanTransactionAmount")
    public ResponseEntity<?> getScanTransactionAmount(){

        BigDecimal amount = BackendApplication.scanTicketCost;
        if(amount != null)
            return ResponseEntity.status(HttpStatus.OK).body(amount);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
