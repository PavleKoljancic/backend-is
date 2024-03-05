package com.app.backend.repositories.tickets;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.tickets.TicketTypeAcceptsDocumentType;
import com.app.backend.models.tickets.TicketTypeAcceptsDocumentTypePrimaryKey;

public interface TicketTypeAcceptsDocumentTypeRepo extends CrudRepository<TicketTypeAcceptsDocumentType, Integer> {
    
    public TicketTypeAcceptsDocumentType findById(TicketTypeAcceptsDocumentTypePrimaryKey ticketTypeAcceptsDocumentTypePrimaryKey);
}
