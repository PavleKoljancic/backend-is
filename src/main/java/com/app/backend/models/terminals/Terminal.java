package com.app.backend.models.terminals;

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
@Entity(name="TERMINAL")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    Boolean isActive;
    @Column(name = "TERMINAL ACTIVATION REQUEST_Id")
    Integer activationRequestID;
    @Column(name = "TRANSPORTER_Id")
    Integer transporterId;
}
