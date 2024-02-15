package com.nhnacademy.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "family_relationship")
public class FamilyRelationship {
    @Id
    @Column(name = "family_resident_serial_number")
    private Integer familyResidentSerialNumber;

    @Column(name = "base_resident_serial_number")
    private Integer baseResidentSerialNumber;

    @Column(name = "family_relationship_code")
    private String familyRelationshipCode;
}
