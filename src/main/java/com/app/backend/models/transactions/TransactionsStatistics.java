package com.app.backend.models.transactions;

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
public class TransactionsStatistics {
    
    private List<TransporterTransactionsStatistics> transporterTransactionsStatistics = new ArrayList<>();
}
