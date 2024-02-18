package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HouseholdMovementAddressRegisterRequest {
    @NotEmpty
    private String houseMovementReportDate;
    @NotEmpty
    private String houseMovementAddress;

    public Date getHouseMovementReportDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(houseMovementReportDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
