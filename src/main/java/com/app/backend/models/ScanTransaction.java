package com.app.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="SCAN_TRANSACTION")
@PrimaryKeyJoinColumn(name = "TRANSACTION_Id", referencedColumnName="Id")
public class ScanTransaction extends Transaction {

    @Column(name = "TERMINAL_Id")
    Integer terminalId;
    
}
