package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.domain.ResidentDto;
import com.nhnacademy.jpa.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, String>, ResidentRepositoryCustom {
    boolean existsByResidentSerialNumber(Integer residentSerialNumber);
    boolean existsByResidentRegistrationNumber(String residentRegistrationNumber);
    Resident findByResidentSerialNumber(Integer residentSerialNumber);
    ResidentDto findByResidentRegistrationNumber(String residentRegistrationNumber);

    @Query(value = "select resident_serial_number from resident order by desc limit 1", nativeQuery = true)
    Integer getLastRegistrationNumber();
}
