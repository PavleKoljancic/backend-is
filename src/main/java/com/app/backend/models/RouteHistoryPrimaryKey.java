package com.app.backend.models;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RouteHistoryPrimaryKey implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FromDateTime")
    Timestamp fromDateTime;

    @Column(name = "ROUTE_Id")
    Integer routeId;

    @Column(name = "TERMINAL_Id")
    Integer terminalId;
}
