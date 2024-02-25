package com.app.backend.repositories.transactions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.transactions.ScanTransaction;

public interface ScanTransactionRepo extends JpaRepository<ScanTransaction,Integer>{

    public List<ScanTransaction> findByTerminalId(Integer terminalId, PageRequest pageRequest); 
    @Procedure(name="addTicketRequest")
    public Integer addScanTransaction(BigDecimal pAmount, Integer pUserId,Integer pTerminalId);
    @Query("SELECT trans FROM TRANSACTION trans "
            + " where trans.Id in (select sTrans.Id from ScanTransaction sTrans"
            + "  inner join TERMINAL ter on ter.Id=sTrans.terminalId"
            +" where :transId=ter.transporterId  AND trans.timestamp >= :startDate)"
            )
    List<ScanTransaction> findScanTransactionsByTransporterId(@Param("transId") Integer transId, @Param("startDate") Timestamp startDate);
}
