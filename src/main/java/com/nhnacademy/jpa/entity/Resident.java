package com.nhnacademy.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "resident")
public class Resident {
    @Id
    @Column(name = "resident_serial_number")
    private Integer residentSerialNumber;

    private String name;

    @Column(name = "resident_registration_number")
    private String residentRegistrationNumber;

    @Column(name = "gender_code")
    private String genderCode;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "birth_place_code")
    private String birthPlaceCode;

    @Column(name = "registration_base_address")
    private String registrationBaseAddress;

    @Column(name = "death_date")
    private Date deathDate;

    @Column(name = "death_place_code")
    private String deathPlaceCode;

    @Column(name = "death_place_address")
    private String deathPlaceAddress;

    // Relationships
    @OneToMany(mappedBy = "baseResident")
    private List<FamilyRelationship> baseRelationshipList;

    @OneToMany(mappedBy = "resident")
    private List<BirthDeathReportResident> reportResidentList;

    @OneToMany(mappedBy = "resident")
    private List<HouseholdCompositionResident> compositionResidentList;
}
