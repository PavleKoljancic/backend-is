package com.app.backend.repositories;


import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.app.backend.models.TerminalActivationRequest;




public interface TerminalActivationRequestRepo extends CrudRepository<TerminalActivationRequest,Integer> {
    List<TerminalActivationRequest> findByTRANSPORTERId(Integer TRANSPORTER_Id); 
}
