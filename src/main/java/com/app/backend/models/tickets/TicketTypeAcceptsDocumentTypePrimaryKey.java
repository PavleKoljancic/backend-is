package com.app.backend.models.tickets;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class TicketTypeAcceptsDocumentTypePrimaryKey {

    @Column(name="DOCUMENT_TYPE_Id")
    public Integer documentTypeId;
    @Column (name="TICKET_TYPE_Id")
    public Integer ticketTypeId;

    public TicketTypeAcceptsDocumentTypePrimaryKey(Integer DocumentTypeId, Integer TicketTypeId) 
    {
        this.ticketTypeId = TicketTypeId;
        this.documentTypeId = DocumentTypeId;
    }

}
