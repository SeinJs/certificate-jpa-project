package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class HouseholdRepositoryImpl extends QuerydslRepositorySupport implements HouseholdRepositoryCustom {

    public HouseholdRepositoryImpl() {
        super(Household.class);
    }

    @Override
    public List<HouseholdCompositionResident> getAllByHouseholdSerialNumber(Integer householdSerialNumber) {
//        return null;
        QHousehold household = QHousehold.household;
        QHouseholdCompositionResident householdCompositionResident = QHouseholdCompositionResident.householdCompositionResident;

        return from(householdCompositionResident)
                .innerJoin(householdCompositionResident.household, household)
                .where(householdCompositionResident.household.householdSerialNumber.eq(householdSerialNumber))
                .select(householdCompositionResident)
                .fetch();
    }

}
