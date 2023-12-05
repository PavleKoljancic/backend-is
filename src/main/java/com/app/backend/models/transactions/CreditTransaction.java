package com.app.backend.models.transactions;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CREDIT_TRANSACTION")
@PrimaryKeyJoinColumn(name = "TRANSACTION_Id", referencedColumnName="Id")
public class CreditTransaction extends Transaction {

    @Column(name = "SUPERVISOR_Id")
    Integer supervisorId;
    
    public CreditTransaction(BigDecimal amount, Timestamp timestamp, Integer userId, Integer supervisorId){
        super(amount, timestamp, userId);
        this.supervisorId = supervisorId;
    }
}
