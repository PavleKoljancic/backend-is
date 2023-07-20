package com.app.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.repositories.TerminalActivationRequestRepo;



@Service
public class TerminalService {

    @Autowired
    TerminalActivationRequestRepo terminalActivationRequestRepo;

    public List<TerminalActivationRequest> findTerminalActivationRequestByTransporterId(Integer TRANSPORTER_Id) 
    {
        return terminalActivationRequestRepo.findByTRANSPORTERId(TRANSPORTER_Id);
    }    

    
}
