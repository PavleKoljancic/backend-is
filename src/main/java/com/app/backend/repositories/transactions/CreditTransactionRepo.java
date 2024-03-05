package com.app.backend.repositories.transactions;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.transactions.CreditTransaction;


public interface CreditTransactionRepo  extends JpaRepository<CreditTransaction,Integer>{
        List<CreditTransaction> findBySupervisorId(Integer supervisorId,PageRequest of);

        @Query("SELECT trans FROM TRANSACTION trans "
        + "WHERE trans.Id IN ("
        + "    SELECT cTrans.Id FROM CreditTransaction cTrans "
        + "    INNER JOIN Supervisor s ON s.Id = cTrans.supervisorId "
        + "    WHERE s.transporterId = :transId "
        + "    AND trans.timestamp >= :startDate )"
        )
        List<CreditTransaction> findCreditTransactionsByTransporterId(@Param("transId") Integer transId, @Param("startDate") Timestamp startDate);
}
