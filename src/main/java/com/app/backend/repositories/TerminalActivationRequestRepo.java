package com.app.backend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.models.TicketType;



public interface TerminalActivationRequestRepo extends CrudRepository<TerminalActivationRequest,String> {
    List<TerminalActivationRequest> findByTRANSPORTERId(Integer TRANSPORTER_Id); 
}
