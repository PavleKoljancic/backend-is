package com.app.backend.models;

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
@Entity(name ="TRANSPORTER")
public class Transporter {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer Id;
    String Name;
}
