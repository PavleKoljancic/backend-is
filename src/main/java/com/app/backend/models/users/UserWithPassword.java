package com.app.backend.models.users;

import java.math.BigDecimal;

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
@Entity(name = "USERWithPassWord")
@Table(name = "USER")
public class UserWithPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private byte[] PictureHash;
    private String email;
    private String firstName;
    private String lastName;
    private BigDecimal Credit = new BigDecimal(0.0);
    private String PasswordHash;
    private String UserKey;
}
