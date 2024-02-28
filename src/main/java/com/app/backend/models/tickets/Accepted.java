package com.app.backend.models.tickets;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="ACCEPTED")
@Table
public class Accepted {
    @EmbeddedId 
    AcceptedPrimaryKey Id;
}
