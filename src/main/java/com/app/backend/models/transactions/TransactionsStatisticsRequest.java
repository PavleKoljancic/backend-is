package com.app.backend.models.transactions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsStatisticsRequest {
 
    private List<Integer> transporterIds;
    private Integer days;
}
