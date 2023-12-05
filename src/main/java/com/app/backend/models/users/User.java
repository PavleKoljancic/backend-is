package com.app.backend.models.users;

import java.math.BigDecimal;
import java.util.List;

import com.app.backend.models.tickets.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    private byte [] PictureHash;
    private String email;
    private String firstName;
    private String lastName;
    private BigDecimal Credit=new BigDecimal(0.0);

}
