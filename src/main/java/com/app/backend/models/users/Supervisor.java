package com.app.backend.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SUPERVISOR")

public class Supervisor {
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive=true;
    @Column(name = "TRANSPORTER_Id")
    private Integer transporterId;

}