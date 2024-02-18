package com.nhnacademy.jpa.domain;

import java.util.Date;

public interface ResidentDto {
    Integer geResidentSerialNumber();
    String getName();
    String getResidentRegistrationNumber();
    String getGenderCode();
    Date getBirthDate();
    String getBirthPlaceCode();
    String getRegistrationBaseAddress();
    Date getDeathDate();
    String getDeathPlaceCode();
    String getDeathPlaceAddress();
}
