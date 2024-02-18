package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.HouseholdMovementAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HouseholdMovementAddressRepository extends JpaRepository<HouseholdMovementAddress, HouseholdMovementAddress.Pk> {
    @Modifying
    @Query("update HouseholdMovementAddress a set a.lastAddressYn='N' where a.lastAddressYn='Y'")
    void changeLatestToN();
}
