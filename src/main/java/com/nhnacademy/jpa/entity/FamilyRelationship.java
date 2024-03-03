package com.nhnacademy.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_relationship")
public class FamilyRelationship {
    @EmbeddedId
    private Pk pk;

    @NonNull
    @Column(name = "family_relationship_code")
    private String familyRelationshipCode;

    //foreign keys
    @MapsId("baseResidentSerialNumber")
    @ManyToOne
    @JoinColumn(name = "base_resident_serial_number")
    private Resident baseResident;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class Pk implements Serializable{
        @Column(name = "base_resident_serial_number")
        private Integer baseResidentSerialNumber;

        @Column(name = "family_resident_serial_number")
        private Integer familyResidentSerialNumber;
    }
}
