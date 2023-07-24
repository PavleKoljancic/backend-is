package com.app.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend.models.Terminal;

public interface TerminalRepo extends JpaRepository<Terminal,Integer>  {
        List<Terminal> findByTransporterId(Integer TransporterId);
        List<Terminal> findByTransporterIdAndIsActiveFalse(Integer TransporterId);
        List<Terminal> findByTransporterIdAndIsActiveTrue(Integer TransporterId);
}
