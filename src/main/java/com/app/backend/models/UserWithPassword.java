package com.app.backend.models;

import java.math.BigDecimal;
import java.util.Base64;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    private String PicturePath;
    private String DocumentPath1;
    private String DocumentPath2;
    private String DocumentPath3;
    private String email;
    private String firstName;
    private String lastName;
    private BigDecimal Credit=new BigDecimal(0.0);
    private byte [] PasswordHash;
    private Integer NumHash; 

    public void setPasswordHash(String Base64PasswordHash) 
    {
        if(Base64PasswordHash!=null)
            this.PasswordHash =Base64.getDecoder().decode(Base64PasswordHash);
    }
}

