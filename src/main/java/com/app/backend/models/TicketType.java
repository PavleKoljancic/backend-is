package com.app.backend.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TICKET_TYPE")
public class TicketType {
    @Id
   private Integer Id;
   private String Name;
   private BigDecimal Cost;
   private String DocumentaionName;
   private Boolean NeedsDocumentaion;
   private Boolean inUse;
}
