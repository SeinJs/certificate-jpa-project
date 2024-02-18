package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class FamilyRelationshipRequest {
    private Integer familyResidentSerialNumber;
    @NotBlank
    private String familyRelationshipCode;
}
