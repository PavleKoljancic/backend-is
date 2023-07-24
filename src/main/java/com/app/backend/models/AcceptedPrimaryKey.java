package com.app.backend.models;

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
    Integer TransporterId;
    @Column (name="TICKET_TYPE_Id")
    Integer TicketTypeId;

    public AcceptedPrimaryKey(Integer TransporterId, Integer TicketTypeId) 
    {
        this.TicketTypeId = TicketTypeId;
        this.TransporterId = TransporterId;
    }
}