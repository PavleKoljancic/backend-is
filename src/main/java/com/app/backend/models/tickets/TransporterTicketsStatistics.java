package com.app.backend.models.tickets;

import java.util.List;

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
    private List<AmountTicketWrapper> amountTickets;
    private List<PeriodicTicketWrapper> periodicTickets;
}
