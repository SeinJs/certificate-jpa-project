package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, Integer>, HouseholdRepositoryCustom {
    @Query(value = "select household_serial_number from household order by desc limit 1", nativeQuery = true)
    Integer getLastSerialNumber();
    boolean existsByResident_ResidentSerialNumber(Integer serialNumber);
}
