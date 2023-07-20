package com.app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SUPERVISOR")
public class SupervisorWithPassword extends Supervisor{
    

    private String PasswordHash;
}
