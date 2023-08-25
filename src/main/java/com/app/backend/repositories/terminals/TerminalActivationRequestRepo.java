package com.app.backend.repositories.terminals;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.terminals.TerminalActivationRequest;




public interface TerminalActivationRequestRepo extends JpaRepository<TerminalActivationRequest,Integer> {
    List<TerminalActivationRequest> findByTransporterIdAndProcessedFalse(Integer TransporterId);
    List<TerminalActivationRequest> findByProcessedFalse();
    List<TerminalActivationRequest>  findBySerialNumberAndProcessedTrue(String serialNumber);  
    List<TerminalActivationRequest>  findBySerialNumberAndProcessedFalse(String serialNumber); 
}
