package com.app.backend.services.transactions;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.transactions.CreditTransaction;
import com.app.backend.models.transactions.ScanTransaction;
import com.app.backend.models.transactions.Transaction;
import com.app.backend.repositories.transactions.CreditTransactionRepo;
import com.app.backend.repositories.transactions.ScanTransactionRepo;
import com.app.backend.repositories.transactions.TransactionRepo;



@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    CreditTransactionRepo creditTransactionRepo;
    @Autowired
    ScanTransactionRepo scanTransactionRepo;

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

    public List<ScanTransaction> findTransactionsByTerminalId(Integer terminalId,PageRequest request) 
    {
        return scanTransactionRepo.findByTerminalId(terminalId,request);
    }

    public List<Transaction> getTransactionsTransporterIdPaged(Integer transporterId, PageRequest pageRequest) 
    {
        return transactionRepo.findTransactionsByTransporterId(transporterId, pageRequest);
    }
    public Integer addScanTransaction(BigDecimal pAmount, Integer pUserId,Integer pTerminalId) 
    {
        return scanTransactionRepo.addScanTransaction(pAmount, pUserId, pTerminalId);
    }

    public Boolean setScanTransactionAmount(BigDecimal newAmount){
        
        File scanTicketFile = new File("configs" + File.separator + "ScanTicket.txt");
        if(scanTicketFile.exists() && scanTicketFile.isFile()){
            try {
                String newCostString = "cost=" + newAmount.toString();
                Files.write(scanTicketFile.toPath(), newCostString.getBytes(), StandardOpenOption.WRITE);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        else 
            return false;
    }

    public BigDecimal getScanTransactionAmount(){

        File scanTicketFile = new File("configs" + File.separator + "ScanTicket.txt");
        if(scanTicketFile.exists() && scanTicketFile.isFile()){
            try {
                String costString = Files.readAllLines(scanTicketFile.toPath()).get(0).split("=")[1];
                BigDecimal cost = new BigDecimal(costString);
                return cost;
            } catch (IOException e) {
                return null;
            }
        }
        else 
            return null;
    }
}
