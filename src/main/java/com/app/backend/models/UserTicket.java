package com.app.backend.models;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "USER_TICKETS")
public class UserTicket {
    @Id
    private Integer TRANSACTION_Id;
    @Column(name = "USER_Id")
    private Integer USERId;
    private Date ValidUntilDate;
    private Integer Usage;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TICKET_TYPE_Id", nullable = false)
    private TicketType type;
}
