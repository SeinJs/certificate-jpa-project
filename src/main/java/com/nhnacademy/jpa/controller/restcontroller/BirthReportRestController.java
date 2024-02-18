package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.entity.BirthDeathReportResident;
import com.nhnacademy.jpa.entity.FamilyRelationship;
import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.BirthDeathReportResidentRepository;
import com.nhnacademy.jpa.repository.FamilyRelationshipRepository;
import com.nhnacademy.jpa.repository.HouseholdCompositionResidentRepository;
import com.nhnacademy.jpa.repository.ResidentRepository;
import com.nhnacademy.jpa.request.BirthDeathReportModifyRequest;
import com.nhnacademy.jpa.request.BirthDeathReportRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residents/{serialNumber}/birth")
public class BirthReportRestController {
    private final BirthDeathReportResidentRepository birthDeathReportResidentRepository;
    private final ResidentRepository residentRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;

    public BirthReportRestController(BirthDeathReportResidentRepository birthDeathReportResidentRepository, ResidentRepository residentRepository, FamilyRelationshipRepository familyRelationshipRepository, HouseholdCompositionResidentRepository householdCompositionResidentRepository) {
        this.birthDeathReportResidentRepository = birthDeathReportResidentRepository;
        this.residentRepository = residentRepository;
        this.familyRelationshipRepository = familyRelationshipRepository;
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
    }

    @PostMapping
    public ResponseEntity<Void> registerBirthReport(@PathVariable("serialNumber") Integer serialNumber, @RequestBody BirthDeathReportRegisterRequest reportRequest){
        // 태어난 resident 생성
        Resident bornResident = new Resident();
        bornResident.setResidentSerialNumber(residentRepository.getLastRegistrationNumber()+1);
        bornResident.setName(reportRequest.getName());
        bornResident.setResidentRegistrationNumber(reportRequest.getRegistrationNumber());
        bornResident.setGenderCode(reportRequest.getGenderCode());
        bornResident.setBirthDate(reportRequest.getParsedBirthDate());
        bornResident.setBirthPlaceCode(reportRequest.getBirthPlaceCode());
        bornResident.setRegistrationBaseAddress(reportRequest.getRegistrationBaseAddress());
        residentRepository.save(bornResident);

        // 신고서 생성
        BirthDeathReportResident birthReportResident = new BirthDeathReportResident();
        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
                reportRequest.getBirthDeathTypeCode(),
                serialNumber,
                bornResident.getResidentSerialNumber()
        );
        birthReportResident.setPk(pk);
        birthReportResident.setBirthDeathReportDate(reportRequest.getParsedReportDate());
        birthReportResident.setBirthReportQualificationCode(reportRequest.getBirthReportQualificationCode());
        if (reportRequest.getEmailAddress() != null){
            birthReportResident.setEmailAddress(reportRequest.getEmailAddress());
        }
        birthReportResident.setPhoneNumber(reportRequest.getPhoneNumber());
        birthReportResident.setResident(bornResident);
        birthDeathReportResidentRepository.save(birthReportResident);

        //가족관계 생성
        // 부, 모
        List<FamilyRelationship> reportersRelationshipList = residentRepository.getResidentsRelationships(serialNumber);
        FamilyRelationship familyRelationship = new FamilyRelationship();
        FamilyRelationship.Pk fPk = new FamilyRelationship.Pk(

        );

        //세대구성원 생성

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{targetSerialNumber}")
    public ResponseEntity<Void> modifyBirthReport(@PathVariable("serialNumber") String serialNumber, @PathVariable("targetSerialNumber") String targetSerialNumber, @RequestBody BirthDeathReportModifyRequest reportRequest){
        BirthDeathReportResident birthDeathReportResident = birthDeathReportResidentRepository.findById(new BirthDeathReportResident.Pk(
                "출생",
                Integer.valueOf(serialNumber),
                Integer.valueOf(targetSerialNumber)
        )).get();

        if (reportRequest.getBirthReportQualificationCode() != null){
            birthDeathReportResident.setBirthReportQualificationCode(reportRequest.getBirthReportQualificationCode());
        }

        if (reportRequest.getEmailAddress() != null){
            birthDeathReportResident.setEmailAddress(reportRequest.getEmailAddress());
        }

        birthDeathReportResident.setPhoneNumber(reportRequest.getPhoneNumber());
        birthDeathReportResidentRepository.save(birthDeathReportResident);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{targetSerialNumber}")
    public ResponseEntity<Void> deleteBirthReport(@PathVariable("serialNumber") String serialNumber, @PathVariable("targetSerialNumber") String targetSerialNumber){
        // TypeCode는 항상 '출생'
        birthDeathReportResidentRepository.delete(birthDeathReportResidentRepository.findById(new BirthDeathReportResident.Pk(
                "출생",
                Integer.valueOf(serialNumber),
                Integer.valueOf(targetSerialNumber)
        )).get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
