package com.app.backend.models;

import java.math.BigDecimal;
import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private String PasswordHash;

    /*public void setPasswordHash(String PasswordHash) 
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, 1000);
        this.PasswordHash = encoder.encode(PasswordHash).getBytes();
    }

    public String getPasswordHashAsString(){
        return Base64.getEncoder().encodeToString(PasswordHash);
    }*/
}

