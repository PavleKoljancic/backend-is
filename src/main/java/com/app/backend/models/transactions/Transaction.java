package com.app.backend.models.transactions;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
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
@Entity(name = "TRANSACTION")
@Inheritance(strategy = InheritanceType.JOINED)
public  class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name="Amount")
    private BigDecimal amount;
    @Column(name="DateAndTime")
    private Timestamp timestamp;
    @Column(name = "USER_Id")
    private Integer userId;
}
