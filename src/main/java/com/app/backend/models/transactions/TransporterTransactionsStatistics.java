package com.app.backend.models.transactions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransporterTransactionsStatistics {
    
    private Integer transporterId;
    private List<TicketTransaction> ticketTransactions;
    private List<ScanTransaction> scanTransactions;
    private List<CreditTransaction> creditTransactions;
}
