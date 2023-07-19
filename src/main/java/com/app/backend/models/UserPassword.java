package com.app.backend.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class UserPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Byte[] PasswordHash;
    private Integer NumHash;

    public boolean isPasswordValid(byte[] PasswordBytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA512");
        md.update(PasswordBytes);
        for (int i = 1; i < NumHash; i++)
            md.update(md.digest());
        byte[] res = md.digest();
        return Arrays.equals(PasswordBytes, res);

    }

}
