package com.app.backend.models.tickets;

import java.sql.Date;

import org.hibernate.annotations.JdbcType;
import org.springframework.data.annotation.TypeAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="DOCUMENT_TYPE")
public class DocumentType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    @Column(name ="Name")
    String name;
    @Column(name="ValidFromDate")
    Date validFromDate;
    @Column(name="ValidUntilDate")
    Date validUntilDate;
}
