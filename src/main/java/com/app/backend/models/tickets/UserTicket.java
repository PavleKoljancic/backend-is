package com.app.backend.models.tickets;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER_TICKETS")
public class UserTicket {
    @Id
    @Column(name = "TRANSACTION_Id")
    private Integer TRANSACTION_Id;
    @Column(name = "USER_Id")
    private Integer USERId;
    private Timestamp ValidUntilDate;
    @Column(name = "currentAmount")
    private Integer Usage;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TICKET_TYPE_Id", nullable = false)
    private TicketType type;
}
