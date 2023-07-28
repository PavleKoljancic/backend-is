package com.app.backend.models.tickets;

import com.app.backend.models.transactions.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TICKET_TRANSACTION")
@PrimaryKeyJoinColumn(name = "TRANSACTION_Id", referencedColumnName="Id")
public class TicketTransaction extends Transaction{

    @Column(name = "TICKET_REQUEST_RESPONSE_Id")
    Integer ticketRequestResponseId;
    
}
