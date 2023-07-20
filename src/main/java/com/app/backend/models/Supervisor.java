package com.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "SUPERVISOR")

public class Supervisor {
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Integer TransporterID;

}