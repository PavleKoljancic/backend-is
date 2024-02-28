package com.app.backend.models.tickets;

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
public class TicketsStatistics {
    
    private List<TransporterTicketsStatistics> transporterTicketsStatistics = new ArrayList<>();
}
