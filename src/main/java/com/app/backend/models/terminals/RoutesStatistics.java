package com.app.backend.models.terminals;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutesStatistics {
    
    private List<RoutesFrequencyStatistics> routesFrequencyStatistics = new ArrayList<>();
}
