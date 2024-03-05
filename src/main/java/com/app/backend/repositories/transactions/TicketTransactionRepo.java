package com.app.backend.repositories.transactions;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.transactions.TicketTransaction;

public interface TicketTransactionRepo extends JpaRepository<TicketTransaction,Integer>{
    
    @Query("SELECT trans FROM TRANSACTION trans "
    + " where trans.Id in ( select tTrans1.Id from"
    + " TicketTransaction tTrans1 inner join TICKET_REQUEST_RESPONSE trr on tTrans1.ticketRequestResponseId = trr.id"
    + " inner join TICKET_REQUEST tr on tr.Id = trr.ticketRequestId "
    + " inner join ACCEPTED a on  a.Id.ticketTypeId = tr.ticketTypeId"
    + " where :transId = a.Id.transporterId AND trans.timestamp >= :startDate) "
    )
    List<TicketTransaction> findTicketTransactionsByTransporterId(@Param("transId") Integer transId, @Param("startDate") Timestamp startDate);
    @Query("SELECT COUNT(trans.Id) AS transactionCount FROM TRANSACTION trans "
    + " where trans.Id in ( select tTrans1.Id from"
    + " TicketTransaction tTrans1 inner join TICKET_REQUEST_RESPONSE trr on tTrans1.ticketRequestResponseId = trr.id"
    + " inner join TICKET_REQUEST tr on tr.Id = trr.ticketRequestId "
    + " where :ticketTypeId = tr.ticketTypeId AND trans.timestamp >= :startDate) "
    )
    Integer findTicketTransactionsCountByTicketTypeId(@Param("ticketTypeId") Integer ticketTypeId, @Param("startDate") Timestamp startDate);
}
