package com.nhnacademy.jpa.request;

import com.nhnacademy.jpa.entity.HouseholdCompositionResident;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class HouseholdRegisterRequest {
    @NotEmpty
    private Integer residentSerialNumber;
    @NotEmpty
    private String householdCompositionDate;
    @NotEmpty
    private String householdCompositionReasonCode;
    @NotEmpty
    private String currentHouseMovementAddress;
    private List<HouseholdCompositionResident> householdCompositionResidentList;

    public Date getParsedCompositionDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(householdCompositionDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
