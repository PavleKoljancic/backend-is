package com.app.backend.models;

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
@Entity(name="TICKET_REQUEST_RESPONSE")
public class TicketRequestResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    @Column(name="SupervisorComment")
    String comment;
    @Column(name="TICKET_REQUEST_Id" )
    Integer ticketRequestId;
    Boolean approved;
}
