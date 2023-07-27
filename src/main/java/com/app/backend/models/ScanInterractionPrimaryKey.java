package com.app.backend.models;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ScanInterractionPrimaryKey implements Serializable {

    @Column(name="ROUTE_HISTORY_FromDateTime")
    Timestamp fromDateTime;

    @Column(name="ROUTE_HISTORY_ROUTE_Id")
    Integer routeHistoryRouteId;

    @Column(name="ROUTE_HISTORY_TERMINAL_Id")
    Integer routeHistoryTerminalId;

    @Column(name="USER_Id")
    Integer userId;

    @Column(name="Time")
    Timestamp time;
}