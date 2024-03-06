package com.app.backend.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.tickets.Document;
import com.app.backend.models.tickets.DocumentType;
import com.app.backend.models.tickets.TicketType;
import com.app.backend.models.tickets.TicketTypeAcceptsDocumentType;
import com.app.backend.models.tickets.TicketTypeAcceptsDocumentTypePrimaryKey;
import com.app.backend.repositories.tickets.DocumentRepo;
import com.app.backend.repositories.tickets.DocumentTypeRepo;
import com.app.backend.repositories.tickets.TicketTypeAcceptsDocumentTypeRepo;
import com.app.backend.repositories.tickets.TicketTypeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    
    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private DocumentTypeRepo documentTypeRepo;

    @Autowired
    private TicketTypeRepo ticketTypeRepo;

    @Autowired
    private TicketTypeAcceptsDocumentTypeRepo ticketTypeAcceptsDocumentTypeRepo;

    public List<Document> getAllUnapprovedDocuments(){

        return documentRepo.findByApprovedEquals(false);
    }

    public boolean changeIsApprovedDocumentId(Integer documentId, boolean isApproved) {

        Optional<Document> result = documentRepo.findById(documentId);
        if (result.isPresent() && result.get().getApproved() != isApproved) {
            Document temp = result.get();
            temp.setApproved(isApproved);
            documentRepo.save(temp);
            return true;
        }
        return false;
    }

    public boolean addDocumentType(DocumentType documentType){
        
        if(documentType.getName() != ""){
            if(documentTypeRepo.save(documentType) != null)
                return true;
        }

        return false;
    }

    public boolean addDocumentTypeToTicketType(Integer docId, Integer ticketId){

        Optional<DocumentType> docResult = documentTypeRepo.findById(docId);
        Optional<TicketType> ticketResult = ticketTypeRepo.findById(ticketId);
        if(docResult.isPresent() && ticketResult.isPresent()){
            if(ticketTypeAcceptsDocumentTypeRepo.findById(new TicketTypeAcceptsDocumentTypePrimaryKey(docId, ticketId)) == null){
                TicketTypeAcceptsDocumentType temp = new TicketTypeAcceptsDocumentType();
                temp.setId(new TicketTypeAcceptsDocumentTypePrimaryKey(docId, ticketId));
                ticketTypeAcceptsDocumentTypeRepo.save(temp);

                return true;
            }

            return false;
        }
        
        return false;
    }

    public List<Document> getDocuments(Integer userId) {
        return documentRepo.findByUserId(userId);
    }
}
