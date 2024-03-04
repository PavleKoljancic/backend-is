package com.app.backend.repositories.tickets;

import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.tickets.DocumentType;

public  interface DocumentTypeRepo extends CrudRepository<DocumentType,Integer>  {
   
}
