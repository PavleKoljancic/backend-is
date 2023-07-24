package com.app.backend.models;


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
@Table(name="PERIODIC_TICKET")
@PrimaryKeyJoinColumn(name = "TICKET_TYPE_Id", referencedColumnName="Id")
public class PeriodicTicket extends TicketType{
    Integer ValidFor;
}
