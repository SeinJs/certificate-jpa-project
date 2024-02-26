package com.nhnacademy.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "household_composition_resident")
public class HouseholdCompositionResident {
    @EmbeddedId
    private Pk pk;

    @Column(name = "report_date")
    private Date reportDate;

    @Column(name = "household_relationship_code")
    private String householdRelationshipCode;

    @Column(name = "household_composition_change_reason_code")
    private String householdCompositionChangeReasonCode;

    @MapsId("householdSerialNumber")
    @ManyToOne
    @JoinColumn(name = "household_serial_number")
    private Household household;

    @MapsId("residentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "resident_serial_number")
    private Resident resident;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk implements Serializable{
        @Column(name = "household_serial_number")
        private Integer householdSerialNumber;

        @Column(name = "resident_serial_number")
        private Integer residentSerialNumber;
    }
}
