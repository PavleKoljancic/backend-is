package com.app.backend.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.tickets.Document;
import com.app.backend.models.tickets.DocumentType;
import com.app.backend.security.SecurityUtil;
import com.app.backend.services.users.DocumentService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/documents")
public class DocumentsController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/getAllUnapprovedDocuments/pagesize={pagesize}size={size}")
    public ResponseEntity<?> getAllUnapprovedDocuments(@PathVariable("pagesize") int page, 
    @PathVariable("size") int size) {
       
        return ResponseEntity.ok().body(documentService.getAllUnapprovedDocuments(PageRequest.of(page, size)));
    }

    @GetMapping("/ChangeisApprovedDocumentId={documentId}")
    public ResponseEntity<?> changeDocumentStatus(@PathVariable("documentId") Integer documentId) {

        return ResponseEntity.ok().body(documentService.changeIsApprovedDocumentId(documentId));
    }

    @PostMapping("/processDocumentRequest")
    public ResponseEntity<?> processDocumentRequest(@RequestBody Document document, HttpServletRequest request) {

        //if(document.getSupervisorId() != SecurityUtil.getIdFromAuthToken(request))
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        return ResponseEntity.ok(documentService.processDocumentRequest(document));
    }
    
    @PostMapping("/addDocumentType")
    public ResponseEntity<?> addDocumentType(@RequestBody DocumentType request) {

        return ResponseEntity.ok().body(documentService.addDocumentType(request));
    }

    @GetMapping("/addDocumentTypes={documentTypeIds}toTicketType={ticketTypeId}")
    public ResponseEntity<?> addDocumentTypeToTicketType(@PathVariable("documentTypeIds") Integer[] documentTypeId, @PathVariable("ticketTypeId") Integer ticketTypeId) {
       
        return ResponseEntity.ok().body(documentService.addDocumentTypeToTicketType(documentTypeId, ticketTypeId));
    }

    @GetMapping(value = "documents&userId={UserId}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("UserId") Integer userId,
            HttpServletRequest request) {

        //if("USER".compareTo(SecurityUtil.getRoleFromAuthToken(request)) == 0 && userId != SecurityUtil.getIdFromAuthToken(request))
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    
        return ResponseEntity.ok(documentService.getDocuments(userId));
    }

    @GetMapping(value = "validDocumentType")
    public ResponseEntity<List<DocumentType>> getDocumentTypes() {
        return ResponseEntity.ok(documentService.getValidDocumentTypes());
    }
}
