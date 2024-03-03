package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.BirthDeathReportResident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthDeathReportResidentRepository extends JpaRepository<BirthDeathReportResident, BirthDeathReportResident.Pk> {
    BirthDeathReportResident findByResident_ResidentSerialNumberAndPk_BirthDeathTypeCode(Integer serialNumber, String typeCode);
}
