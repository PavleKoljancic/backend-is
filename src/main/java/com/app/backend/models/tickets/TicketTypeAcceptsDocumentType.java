package com.app.backend.models.tickets;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TICKET_TYPE_ACCEPTS_DOCUMENT_TYPE")
public class TicketTypeAcceptsDocumentType {

    @EmbeddedId 
    TicketTypeAcceptsDocumentTypePrimaryKey Id;
    
}
