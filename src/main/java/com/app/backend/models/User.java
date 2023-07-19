package com.app.backend.models;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
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
    private String PicturePath;
    private String DocumentPath1;
    private String DocumentPath2;
    private String DocumentPath3;
    private String email;
    private String firstName;
    private String lastName;
    private BigDecimal Credit=new BigDecimal(0.0);

}
