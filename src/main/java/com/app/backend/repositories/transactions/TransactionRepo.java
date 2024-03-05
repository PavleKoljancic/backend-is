package com.app.backend.repositories.transactions;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.transactions.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByTimestampGreaterThan(Timestamp dateAndTime);

    List<Transaction> findByTimestampGreaterThan(Timestamp dateAndTime, PageRequest pageRequest);

    List<Transaction> findByTimestampGreaterThanEqualAndTimestampLessThanEqual(Timestamp start, Timestamp end,
            PageRequest page);

    List<Transaction> findByUserId(Integer userId, PageRequest pageRequest);

    @Query("SELECT trans FROM TRANSACTION trans "
            + " where trans.Id in ( select tTrans1.Id from"
            + " TicketTransaction tTrans1 inner join TICKET_REQUEST_RESPONSE trr on tTrans1.ticketRequestResponseId = trr.id"
            + " inner join TICKET_REQUEST tr on tr.Id = trr.ticketRequestId "
            + " inner join ACCEPTED a on  a.Id.ticketTypeId = tr.ticketTypeId"
            + " where :transId = a.Id.transporterId ) "
            + " or trans.Id in"
            + "( select cTrans.Id from CreditTransaction cTrans "
            +" inner join Supervisor s on s.Id = cTrans.supervisorId"
            +" where s.transporterId = :transId )"
            + " or trans.id in"
            + "(select sTrans.Id from ScanTransaction sTrans"
            + "  inner join TERMINAL ter on ter.Id=sTrans.terminalId"
            +" where :transId=ter.transporterId)"
            )
    List<Transaction> findTransactionsByTransporterId(@Param("transId") Integer transId, PageRequest pageRequest);    
}
