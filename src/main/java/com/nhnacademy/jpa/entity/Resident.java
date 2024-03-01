package com.nhnacademy.jpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "resident")
public class Resident {
    @Id
    @Column(name = "resident_serial_number")
    private Integer residentSerialNumber;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "resident_registration_number")
    private String residentRegistrationNumber;

    @NotNull
    @Column(name = "gender_code")
    private String genderCode;

    @NotNull
    @Column(name = "birth_date")
    private Date birthDate;

    @NotNull
    @Column(name = "birth_place_code")
    private String birthPlaceCode;

    @NotNull
    @Column(name = "registration_base_address")
    private String registrationBaseAddress;

    @Column(name = "death_date")
    private Date deathDate;

    @Column(name = "death_place_code")
    private String deathPlaceCode;

    @Column(name = "death_place_address")
    private String deathPlaceAddress;

    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
    // Relationships
//    @OneToMany(mappedBy = "baseResident")
//    private List<FamilyRelationship> baseRelationshipList;
//
//    @OneToMany(mappedBy = "resident")
//    private List<BirthDeathReportResident> reportResidentList;
//
//    @OneToMany(mappedBy = "resident")
//    private List<HouseholdCompositionResident> compositionResidentList;
}
