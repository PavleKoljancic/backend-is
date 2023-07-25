package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.app.backend.models.TicketRequest;

public interface TicketRequestRepo extends JpaRepository<TicketRequest,Integer> {
    @Procedure(name="addTicketRequest")
    public Integer addTicketRequest(Integer pUserId,Integer pTicketTypeId);
    @Query(
  value = "SELECT TICKET_REQUEST.* FROM TICKET_REQUEST inner join ACCEPTED" 
          +" on( ACCEPTED.TICKET_TYPE_Id = TICKET_REQUEST.TICKET_TYPE_Id) INNER JOIN `TICKET_TYPE` on(TICKET_TYPE.`Id`=TICKET_REQUEST.TICKET_TYPE_Id)"
        +" where TICKET_TYPE.`NeedsDocumentaion`=true"  
          +" and TICKET_REQUEST.Id not in (select TICKET_REQUEST_Id from TICKET_REQUEST_RESPONSE) and ACCEPTED.`TRANSPORTER_Id`= :transporterId",nativeQuery = true )
      public List<TicketRequest> getTicketRequestByTransporterId(@Param("transporterId")Integer transporterId);
}
