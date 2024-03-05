package com.app.backend.repositories.tickets;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.tickets.TicketType;



public interface TicketTypeRepo extends JpaRepository<TicketType,Integer>  {

    List<TicketType> findByInUseTrue(PageRequest pageRequest);

    @Query(
        "select tt.Name from TICKET_TYPE tt inner join USER_TICKETS ut on ut.type.Id = tt.Id where ut.TRANSACTION_Id = :transactionId"
    )
    public String findTicketTypeByTransactionId(@Param("transactionId") Integer transactionId);
}
