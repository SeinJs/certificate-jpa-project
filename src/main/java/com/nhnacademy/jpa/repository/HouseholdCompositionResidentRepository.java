package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.Household;
import com.nhnacademy.jpa.entity.HouseholdCompositionResident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseholdCompositionResidentRepository extends JpaRepository<HouseholdCompositionResident, HouseholdCompositionResident.Pk> {
//    @Query(value = "select household_serial_number from household order by desc limit 1", nativeQuery = true)
//    Integer getLastSerialNumber();
}
