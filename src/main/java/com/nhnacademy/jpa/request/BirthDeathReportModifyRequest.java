package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class BirthDeathReportModifyRequest {
    private String birthReportQualificationCode;
    private String deathReportQualificationCode;
    @Email
    private String emailAddress;
    @NotEmpty
    private String phoneNumber;
}
