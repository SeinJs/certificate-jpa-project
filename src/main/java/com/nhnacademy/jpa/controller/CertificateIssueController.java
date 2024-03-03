package com.nhnacademy.jpa.controller;

import com.nhnacademy.jpa.domain.CertificateTypeDto;
import com.nhnacademy.jpa.entity.*;
import com.nhnacademy.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CertificateIssueController {
    private final ResidentRepository residentRepository;
    private final CertificateIssueRepository certificateIssueRepository;
    private final HouseholdRepository householdRepository;
    private final HouseholdCompositionResidentRepository compositionResidentRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final HouseholdMovementAddressRepository addressRepository;
    private final BirthDeathReportResidentRepository birthDeathReportResidentRepository;


    @GetMapping("/report/resident")
    public String getResidentCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        CertificateIssue certificateDetails = certificateIssueRepository.findByResident_ResidentSerialNumberAndCertificateTypeCode(residentSerialNumber, "주민등록등본");
        Household household = householdRepository.findById(residentSerialNumber).get();

        List<HouseholdCompositionResident> compositionResidents = householdRepository.getAllByHouseholdSerialNumber(household.getHouseholdSerialNumber());
        List<HouseholdMovementAddress> addresses = addressRepository.findAllByHousehold_HouseholdSerialNumber(household.getHouseholdSerialNumber());

        model.addAttribute("certificateDetails", certificateDetails);
        model.addAttribute("compositionResidents", compositionResidents);
        model.addAttribute("addresses", addresses);

        return "certificate_view/resident_registration_doc";
    }

    @GetMapping("/report/family")
    public String getFamilyCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        CertificateIssue certificateDetails = certificateIssueRepository.findByResident_ResidentSerialNumberAndCertificateTypeCode(residentSerialNumber, "가족관계증명서");
        Household household = householdRepository.findById(residentSerialNumber).get();

        List<FamilyRelationship> relationships = familyRelationshipRepository.findAllByBaseResident_ResidentSerialNumber(residentSerialNumber);

        model.addAttribute("certificateDetails", certificateDetails);
        model.addAttribute("household", household);
        model.addAttribute("relationships", relationships);

        return "certificate_view/family_relationship_doc";
    }

    @GetMapping("/report/birth")
    public String getBirthCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        CertificateIssue certificateDetails = certificateIssueRepository.findByResident_ResidentSerialNumberAndCertificateTypeCode(residentSerialNumber, "출생신고서");
        BirthDeathReportResident birthResident = birthDeathReportResidentRepository.findByResident_ResidentSerialNumberAndPk_BirthDeathTypeCode(residentSerialNumber, "출생");

        return "certificate_view/birth_report_doc";
    }

    @GetMapping("/report/death")
    public String getDeathCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        CertificateIssue certificateDetails = certificateIssueRepository.findByResident_ResidentSerialNumberAndCertificateTypeCode(residentSerialNumber, "사망신고서");
        birthDeathReportResidentRepository.findByResident_ResidentSerialNumberAndPk_BirthDeathTypeCode(residentSerialNumber, "사망");
        return "certificate_view/death_report_doc";
    }

    @GetMapping("/report/list")
    public String getIssueList(@Param("residentSerialNumber") Integer residentSerialNumber, Model model, Pageable pageable){
        List<CertificateTypeDto> issues = certificateIssueRepository.findByResident_ResidentSerialNumber(residentSerialNumber, pageable).getContent();
        model.addAttribute("issues", issues);
        return "issue_details";
    }
}
