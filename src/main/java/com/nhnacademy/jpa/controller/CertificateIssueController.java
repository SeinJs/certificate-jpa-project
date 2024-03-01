package com.nhnacademy.jpa.controller;

import com.nhnacademy.jpa.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CertificateIssueController {
    private final ResidentRepository residentRepository;

    @GetMapping("/report/resident")
    public String getResidentCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){

        return "certificate_view/resident_registration_doc";
    }

    @GetMapping("/report/family")
    public String getFamilyCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        return "certificate_view/family_relationship_doc";
    }

    @GetMapping("/report/birth")
    public String getBirthCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        return "certificate_view/birth_report_doc";
    }

    @GetMapping("/report/death")
    public String getDeathCertificate(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){
        return "certificate_view/death_report_doc";
    }

    @GetMapping("/report/list")
    public String getIssueList(@Param("residentSerialNumber") Integer residentSerialNumber, Model model){

        return "issue_details";
    }
}
