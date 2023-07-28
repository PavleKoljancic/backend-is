package com.app.backend.repositories.transactions;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.app.backend.models.transactions.ScanTransaction;

public interface ScanTransactionRepo extends JpaRepository<ScanTransaction,Integer>{

    public List<ScanTransaction> findByTerminalId(Integer terminalId, PageRequest pageRequest); 
    @Procedure(name="addTicketRequest")
    public Integer addScanTransaction(BigDecimal pAmount, Integer pUserId,Integer pTerminalId);
}
