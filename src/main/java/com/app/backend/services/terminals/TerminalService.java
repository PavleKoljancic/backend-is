package com.app.backend.services.terminals;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.backend.models.terminals.Terminal;
import com.app.backend.models.terminals.TerminalActivationRequest;
import com.app.backend.repositories.terminals.TerminalActivationRequestRepo;
import com.app.backend.repositories.terminals.TerminalRepo;



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

    public Optional<Terminal> getById(Integer terminalId){
        return terminalRepo.findById(terminalId);
    }

   public List<Terminal> getTerminalByTransporterId(Integer TransporterId) 
    {
        return terminalRepo.findByTransporterId(TransporterId);
    }
   public List<Terminal> getNotInUSeByTransporterId(Integer TransporterId) 
    {
        return terminalRepo.findByTransporterIdAndIsActiveFalse(TransporterId);
    }
  public  List<Terminal> getInUseByTransporterId(Integer TransporterId)
    {

        return terminalRepo.findByTransporterIdAndIsActiveTrue(TransporterId);
    }

    public boolean processTerminalActivationRequest(Integer aRId, Boolean approval) {
        Optional<TerminalActivationRequest> Result = terminalActivationRequestRepo.findById(aRId);
        if(!Result.isPresent())
            return false;
        TerminalActivationRequest terminalActivationRequest = Result.get();
        if(terminalActivationRequest.getProcessed())
            return false;
        terminalActivationRequest.setProcessed(true);
        terminalActivationRequestRepo.save(terminalActivationRequest);
        if(approval==true){
            Terminal terminal = new Terminal();
            terminal.setActivationRequestID(terminalActivationRequest.getId());
            terminal.setIsActive(true);
            terminal.setTransporterId(terminalActivationRequest.getTransporterId());
            terminalRepo.save(terminal);
        }
        return true;
    }

    public boolean ChangeIsActiveTerminalId(Integer terminalId, Boolean isActive) {
        
        Optional<Terminal> reuslt = terminalRepo.findById(terminalId);
        if(reuslt.isPresent()&&reuslt.get().getIsActive()!=isActive)
        {
            Terminal temp = reuslt.get();
            temp.setIsActive(isActive);
            terminalRepo.save(temp);
            return true;
        }
        return false;
    }

    public Terminal findBySerialNumber(String serialNumber) {
        List<TerminalActivationRequest> tars = terminalActivationRequestRepo.findBySerialNumberAndProcessedTrue(serialNumber);
        for( TerminalActivationRequest tar : tars)
        {
            Terminal res = terminalRepo.findByActivationRequestID(tar.getId());
            if(res!=null)
                return res;
        
        }
        return null;
    }

}
