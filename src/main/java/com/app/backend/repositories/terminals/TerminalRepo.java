package com.app.backend.repositories.terminals;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.terminals.Terminal;

public interface TerminalRepo extends JpaRepository<Terminal,Integer>  {
        List<Terminal> findByTransporterId(Integer TransporterId);
        List<Terminal> findByTransporterIdAndIsActiveFalse(Integer TransporterId);
        List<Terminal> findByTransporterIdAndIsActiveTrue(Integer TransporterId);
}
