package com.app.backend.models;

import org.springframework.context.annotation.Primary;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ACCEPTED")
public class Accepted {
    @EmbeddedId AcceptedPrimaryKey primaryKey;
}
