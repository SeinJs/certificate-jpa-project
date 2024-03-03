package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.HouseholdMovementAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HouseholdMovementAddressRepository extends JpaRepository<HouseholdMovementAddress, HouseholdMovementAddress.Pk> {
    @Transactional
    @Modifying
    @Query(value = "update household_movement_address set last_address_yn='N' where last_address_yn='Y' and household_serial_number=?1", nativeQuery = true)
    void changeLatestToN(@Param("household_serial_number") Integer serialNumber);

    List<HouseholdMovementAddress> findAllByHousehold_HouseholdSerialNumber(Integer serialNumber);
}
