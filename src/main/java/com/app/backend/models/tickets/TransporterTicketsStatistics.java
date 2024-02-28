package com.app.backend.models.tickets;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransporterTicketsStatistics {
    
    private Integer transporterId;
    private HashMap<AmountTicket, Integer> amountTickets = new HashMap<>();
    private HashMap<PeriodicTicket, Integer> periodicTickets = new HashMap<>();
}
