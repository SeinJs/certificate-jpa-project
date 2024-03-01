package com.nhnacademy.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "household")
public class Household {
    @Id
    @Column(name = "household_serial_number")
    private Integer householdSerialNumber;

    @ManyToOne
    @JoinColumn(name = "household_resident_serial_number")
    private Resident resident;

    @NonNull
    @Column(name = "household_composition_date")
    private Date householdCompositionDate;

    @NonNull
    @Column(name = "household_composition_reason_code")
    private String householdCompositionReasonCode;

    @NonNull
    @Column(name = "current_house_movement_address")
    private String currentHouseMovementAddress;

    // Relationships
//    @OneToMany(mappedBy = "household")
//    private List<HouseholdCompositionResident> householdCompositionResidentList;
//
//    @OneToMany(mappedBy = "household")
//    private List<HouseholdMovementAddress> householdMovementAddressList;
}
