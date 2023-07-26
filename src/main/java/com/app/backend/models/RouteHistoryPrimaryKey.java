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
public class RouteHistoryPrimaryKey implements Serializable {


    @Column(name = "FromDateTime")
    Timestamp fromDateTime;

    @Column(name = "ROUTE_Id")
    Integer routeId;

    @Column(name = "TERMINAL_Id")
    Integer terminalId;
}
