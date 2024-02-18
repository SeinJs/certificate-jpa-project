package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ResidentRegisterRequest {
    @Min(0)
    private Integer residentSerialNumber;
    @NotEmpty
    private String name;
    @NotEmpty
    private String residentRegistrationNumber;
    @NotEmpty
    private String genderCode;
    @NotEmpty
    private String birthDate;
    @NotEmpty
    private String birthPlaceCode;
    @NotEmpty
    private String registrationBaseAddress;
    private String deathDate;
    private String deathPlaceCode;
    private String deathPlaceAddress;

    public Date getParsedBirthDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(birthDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getParsedDeathDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(deathDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
