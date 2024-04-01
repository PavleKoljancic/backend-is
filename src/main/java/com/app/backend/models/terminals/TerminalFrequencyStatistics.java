package com.app.backend.models.terminals;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalFrequencyStatistics {
    
    private Integer terminalId;
    private HashMap<String, List<Timestamp>> scans = new HashMap<>();
}
