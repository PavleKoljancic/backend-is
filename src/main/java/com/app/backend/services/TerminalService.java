package com.app.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.repositories.TerminalActivationRequestRepo;



@Service
public class TerminalService {

    @Autowired
    TerminalActivationRequestRepo terminalActivationRequestRepo;

    public List<TerminalActivationRequest> findTerminalActivationRequestByTransporterId(Integer TRANSPORTER_Id) 
    {
        return terminalActivationRequestRepo.findByTRANSPORTERIdAndProcessedFalse(TRANSPORTER_Id);
    }    

    public List<TerminalActivationRequest> getAllPending() 
    {
        return terminalActivationRequestRepo.findByProcessedFalse();
    }

    public Integer  addTerminalActivationRequest(TerminalActivationRequest terminalActivationRequest)
    {
        return terminalActivationRequestRepo.save(terminalActivationRequest).getId();
    }

    public List<TerminalActivationRequest> getAll() 
    {
        return terminalActivationRequestRepo.findAll();
    }
}
