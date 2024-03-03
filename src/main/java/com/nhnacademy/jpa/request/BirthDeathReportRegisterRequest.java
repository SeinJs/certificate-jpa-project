package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class BirthDeathReportRegisterRequest {
    private String name;
    private String genderCode;
    private String birthDate;
    private String birthPlaceCode;
    private String registrationBaseAddress;

    @NotEmpty
    private String birthDeathTypeCode;
    @NotEmpty
    private Integer residentSerialNumber;
    @NotEmpty
    private Integer reportResidentSerialNumber;
    @NotEmpty
    private String birthDeathReportDate;

    private String birthReportQualificationCode;

    private String deathReportQualificationCode;

    private String emailAddress;
    @NotEmpty
    private String phoneNumber;

    public String getRegistrationNumber(){
        Date date = getParsedBirthDate();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");

        return format.format(date)+"-******";
    }

    public Date getParsedBirthDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(birthDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getParsedReportDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(birthDeathReportDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
