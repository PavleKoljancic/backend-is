package com.app.backend.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.TerminalActivationRequest;




public interface TerminalActivationRequestRepo extends JpaRepository<TerminalActivationRequest,Integer> {
    List<TerminalActivationRequest> findByTRANSPORTERIdAndProcessedFalse(Integer TRANSPORTER_Id);
    List<TerminalActivationRequest> findByProcessedFalse();  
}
