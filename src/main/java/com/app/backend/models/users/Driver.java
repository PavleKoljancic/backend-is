package com.app.backend.models.users;

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
@Table(name="DRIVER")
@PrimaryKeyJoinColumn(name = "PIN_USER_Id", referencedColumnName="Id")

public class Driver extends PinUser{
    @Column(name = "TRANSPORTER_Id")
    Integer transporterId;
}
