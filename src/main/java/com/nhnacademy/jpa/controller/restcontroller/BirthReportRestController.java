package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.entity.BirthDeathReportResident;
import com.nhnacademy.jpa.entity.FamilyRelationship;
import com.nhnacademy.jpa.entity.HouseholdCompositionResident;
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

import java.util.Objects;

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
        Resident bornResident = residentRepository.findByResidentSerialNumber(reportRequest.getResidentSerialNumber());

        // 신고서 생성
        BirthDeathReportResident birthReportResident = new BirthDeathReportResident();
        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
                reportRequest.getBirthDeathTypeCode(),
                serialNumber,
                reportRequest.getResidentSerialNumber()
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
        FamilyRelationship spouse = familyRelationshipRepository.findByBaseResident_ResidentSerialNumberAndFamilyRelationshipCode(serialNumber, "배우자");

        // 자녀 -> 부, 모
        familyRelationshipRepository.save(new FamilyRelationship(
                new FamilyRelationship.Pk(bornResident.getResidentSerialNumber(), serialNumber),
                reportRequest.getBirthReportQualificationCode(),
                bornResident
        ));

        familyRelationshipRepository.save(new FamilyRelationship(
                new FamilyRelationship.Pk(bornResident.getResidentSerialNumber(), spouse.getPk().getFamilyResidentSerialNumber()),
                Objects.equals(reportRequest.getBirthReportQualificationCode(), "부") ? "모" : "부",
                bornResident
        ));

        // 자녀 <- 부, 모
        familyRelationshipRepository.save(new FamilyRelationship(
                new FamilyRelationship.Pk(serialNumber, bornResident.getResidentSerialNumber()),
                "자녀",
                bornResident
        ));

        familyRelationshipRepository.save(new FamilyRelationship(
                new FamilyRelationship.Pk(spouse.getPk().getFamilyResidentSerialNumber(), bornResident.getResidentSerialNumber()),
                "자녀",
                bornResident
        ));

        //세대구성원 생성
        HouseholdCompositionResident compositionResident = householdCompositionResidentRepository.findByResident_ResidentSerialNumber(serialNumber);
        householdCompositionResidentRepository.save(new HouseholdCompositionResident(
                new HouseholdCompositionResident.Pk(
                        compositionResident.getHousehold().getHouseholdSerialNumber(),
                        reportRequest.getResidentSerialNumber()
                ),
                reportRequest.getParsedReportDate(),
                "자녀",
                "출생등록",
                compositionResident.getHousehold(),
                bornResident
        ));

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
