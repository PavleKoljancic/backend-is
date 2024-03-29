package com.app.backend.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PIN_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class PinUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    String pin;
    String Firstname;
    String Lastname;
    Boolean isActive = true;
    String JMB;
    String Password;
}
