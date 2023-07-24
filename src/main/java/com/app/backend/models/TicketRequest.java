package com.app.backend.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="TICKET_REQUEST")
public class TicketRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    Date DateTime;
    @Column(name="USER_Id")
    Integer userId ;
    @Column(name="TICKET_TYPE_Id")
    Integer ticketTypeId ;
}