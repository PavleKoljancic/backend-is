package com.app.backend.models.tickets;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="AMOUNT_TICKET")
@Table
@PrimaryKeyJoinColumn(name = "TICKET_TYPE_Id", referencedColumnName="Id")
public class AmountTicket extends TicketType{
    
    Integer Amount;
}
