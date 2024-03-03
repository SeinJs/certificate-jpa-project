package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class ResidentModifyRequest {
    private String name;
    private String residentRegistrationNumber;
    private String genderCode;
    private Date deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;
    private String email;
    private String id;
    private String password;
}
