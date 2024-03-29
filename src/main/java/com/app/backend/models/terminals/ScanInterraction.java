package com.app.backend.models.terminals;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="SCAN_INTERACTION")
public class ScanInterraction {
    
    @EmbeddedId ScanInterractionPrimaryKey id;
    
    
    @Column(name="TRANSACTION_Id")
    private Integer transactionId;
       

    
    
}
