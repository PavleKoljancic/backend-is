package com.app.backend.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.tickets.DocumentType;
import com.app.backend.services.users.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/documents")
public class DocumentsController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/getAllUnapprovedDocuments")
    public ResponseEntity<?> getAllUnapprovedDocuments() {
       
        return ResponseEntity.ok().body(documentService.getAllUnapprovedDocuments());
    }

    @GetMapping("/ChangeisApprovedDocumentId={documentId}andIsApproved={isApproved}")
    public ResponseEntity<?> changeDocumentStatus(@PathVariable("documentId") Integer documentId, @PathVariable("isApproved") Boolean isApproved) {

        return ResponseEntity.ok().body(documentService.changeIsApprovedDocumentId(documentId, isApproved));
    }
    
    @PostMapping("/addDocumentType")
    public ResponseEntity<?> addDocumentType(@RequestBody DocumentType request) {

        return ResponseEntity.ok().body(documentService.addDocumentType(request));
    }

    @GetMapping("/addDocumentType={documentTypeId}toTicketType={ticketTypeId}")
    public ResponseEntity<?> addDocumentTypeToTicketType(@PathVariable("documentTypeId") Integer documentTypeId, @PathVariable("ticketTypeId") Integer ticketTypeId) {
       
        return ResponseEntity.ok().body(documentService.addDocumentTypeToTicketType(documentTypeId, ticketTypeId));
    }
    
}
