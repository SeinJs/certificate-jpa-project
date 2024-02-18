package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.HouseholdCompositionResident;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface HouseholdRepositoryCustom{
    List<HouseholdCompositionResident> getAllByHouseholdSerialNumber(Integer householdSerialNumber);
}
