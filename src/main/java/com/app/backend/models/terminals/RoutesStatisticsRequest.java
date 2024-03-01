package com.app.backend.models.terminals;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutesStatisticsRequest {
    
    private List<Integer> routeIds;
    private Timestamp timeFrom;
    private Timestamp timeUntil;

}
