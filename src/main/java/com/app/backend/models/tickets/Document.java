package com.app.backend.models.tickets;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="DOCUMENT")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;
    @Column(name ="USER_Id")
    Integer userId;
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_TYPE_Id", nullable = false)
    private DocumentType documentType;
    @Column(name = "Approved")
    Boolean approved;
    
    
}
