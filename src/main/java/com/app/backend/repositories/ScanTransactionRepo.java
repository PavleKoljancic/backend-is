package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.ScanTransaction;

public interface ScanTransactionRepo extends JpaRepository<ScanTransaction,Integer>{

    public List<ScanTransaction> findByTerminalId(Integer terminalId, PageRequest pageRequest); 
    
}