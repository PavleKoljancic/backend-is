package com.app.backend.models.tickets;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AcceptedPrimaryKey implements Serializable {
    @Column(name="TRANSPORTER_Id")
    public Integer transporterId;
    @Column (name="TICKET_TYPE_Id")
    public Integer ticketTypeId;

    public AcceptedPrimaryKey(Integer TransporterId, Integer TicketTypeId) 
    {
        this.ticketTypeId = TicketTypeId;
        this.transporterId = TransporterId;
    }
}