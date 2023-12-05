package com.app.backend.models.tickets;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.DialectOverride.JoinFormula;
import org.hibernate.annotations.WhereJoinTable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TICKET_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = AmountTicket.class, name = "amount"),
        @JsonSubTypes.Type(value = PeriodicTicket.class, name = "periodic")})
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String Name;
    private BigDecimal Cost;
    private Boolean NeedsDocumentaion;
    private Boolean inUse;
    @OneToMany
    @JoinTable(name = "TICKET_TYPE_ACCEPTS_DOCUMENT_TYPE" ,joinColumns = {@JoinColumn(name="TICKET_TYPE_Id",referencedColumnName = "Id")}
    , inverseJoinColumns = {@JoinColumn(name="DOCUMENT_TYPE_Id", referencedColumnName = "Id")})
    @WhereJoinTable(clause = "d1_1.ValidFromDate <= CURRENT_DATE and CURRENT_DATE  <= d1_1.ValidUntilDate ")
    
    private List<DocumentType> documents ;
}
