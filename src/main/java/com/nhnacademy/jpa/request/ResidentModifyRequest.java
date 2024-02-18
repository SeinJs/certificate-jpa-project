package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class ResidentModifyRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String residentRegistrationNumber;
    @NotEmpty
    private String genderCode;
    private Date deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;
}
