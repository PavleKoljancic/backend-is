package com.app.backend.models.terminals;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="ROUTE_HISTORY")
public class RouteHistory {
    
    @EmbeddedId RouteHistoryPrimaryKey primaryKey;

    @Column(name = "DRIVER_PIN_USER_Id")
    Integer driverId;

    Timestamp toDateTime;
}
