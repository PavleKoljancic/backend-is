package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Terminal;
import com.app.backend.models.TerminalActivationRequest;

public interface TerminalRepo extends JpaRepository<Terminal,Integer>  {
        List<TerminalActivationRequest> findByTransporterId(Integer TransporterId);
        List<TerminalActivationRequest> findByTransporterIdAndIsActiveFalse(Integer TransporterId);
        List<TerminalActivationRequest> findByTransporterIdAndIsActiveTrue(Integer TransporterId);
}
