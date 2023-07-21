package com.app.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="TERMINAL ACTIVATION REQUEST")
public class TerminalActivationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    String SerialNumber;
    @Column(name = "TRANSPORTER_Id")
    Integer TRANSPORTERId;
    @Column(name="Processed")
    Boolean processed=false;
    //Razmisli kako ces ih pretrazivati
}
