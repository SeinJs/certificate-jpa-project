package com.nhnacademy.jpa.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HouseholdMovementAddressModifyRequest {
    private String houseMovementAddress;
    private String lastAddressYn;
}
