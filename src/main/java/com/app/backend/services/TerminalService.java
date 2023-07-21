package com.app.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.backend.models.Terminal;
import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.repositories.TerminalActivationRequestRepo;
import com.app.backend.repositories.TerminalRepo;



@Service
public class TerminalService {

    @Autowired
    TerminalActivationRequestRepo terminalActivationRequestRepo;
    @Autowired
    TerminalRepo terminalRepo;


    public List<TerminalActivationRequest> getTerminalActivationRequestByTransporterId(Integer TRANSPORTER_Id) 
    {
        return terminalActivationRequestRepo.findByTransporterIdAndProcessedFalse(TRANSPORTER_Id);
    }    

    public List<TerminalActivationRequest> getAllPendingActivationRequests() 
    {
        return terminalActivationRequestRepo.findByProcessedFalse();
    }

    public Integer  addTerminalActivationRequest(TerminalActivationRequest terminalActivationRequest)
    {
        return terminalActivationRequestRepo.save(terminalActivationRequest).getId();
    }

    public List<TerminalActivationRequest> getAllActivationRequests() 
    {
        return terminalActivationRequestRepo.findAll();
    }

    List<TerminalActivationRequest> getTerminalByTransporterId(Integer TransporterId) 
    {
        return terminalRepo.findByTransporterId(TransporterId);
    }
    List<TerminalActivationRequest> getNotInUSeByTransporterId(Integer TransporterId) 
    {
        return terminalRepo.findByTransporterIdAndInUseFalse(TransporterId);
    }
    List<TerminalActivationRequest> getInUSeByTransporterId(Integer TransporterId)
    {

        return terminalRepo.findByTransporterIdAndInUseTrue(TransporterId);
    }
}
