package com.app.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend.models.TerminalActivationRequest;
import com.app.backend.repositories.TerminalActivationRequestRepo;
import com.app.backend.services.TerminalService;

@RestController
@RequestMapping("/api/terminals")
public class TerminalController {
    @Autowired
    TerminalService terminalService;

        @Autowired
    TerminalActivationRequestRepo terminalActivationRequestRepo;

    @GetMapping("/getByTransporterdId={transporterID}")
    public List<TerminalActivationRequest> findTerminalActivationRequestByTransporterId(@PathVariable("transporterID") Integer TRANSPORTER_Id) 
    {
        return terminalActivationRequestRepo.findByTRANSPORTERIdAndProcessedFalse(TRANSPORTER_Id);
    }    

    @GetMapping("/getAllPending")
    public List<TerminalActivationRequest> getAllPending() 
    {
        return terminalService.getAllPending();
    }

    @PostMapping("add/activationrequest")
    public Integer  addTerminalActivationRequest(@RequestBody TerminalActivationRequest terminalActivationRequest)
    {
        return terminalService.addTerminalActivationRequest(terminalActivationRequest);
    }

    @GetMapping("/getAll")    
    public List<TerminalActivationRequest> getAll() 
    {
        return terminalService.getAll();
    }
}
