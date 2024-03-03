package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.domain.CertificateTypeDto;
import com.nhnacademy.jpa.entity.CertificateIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateIssueRepository extends JpaRepository<CertificateIssue, Long> {
    CertificateIssue findByResident_ResidentSerialNumberAndCertificateTypeCode(Integer serialNumber, String typeCode);
    Page<CertificateTypeDto> findByResident_ResidentSerialNumber(Integer serialNumber, Pageable pageable);
}
