package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.entity.BirthDeathReportResident;
import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.BirthDeathReportResidentRepository;
import com.nhnacademy.jpa.repository.ResidentRepository;
import com.nhnacademy.jpa.request.BirthDeathReportModifyRequest;
import com.nhnacademy.jpa.request.BirthDeathReportRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/residents/{serialNumber}/death")
public class DeathReportRestController {
    private final BirthDeathReportResidentRepository birthDeathReportResidentRepository;
    private final ResidentRepository residentRepository;

    public DeathReportRestController(BirthDeathReportResidentRepository birthDeathReportResidentRepository, ResidentRepository residentRepository) {
        this.birthDeathReportResidentRepository = birthDeathReportResidentRepository;
        this.residentRepository = residentRepository;
    }

    /**
     * 이미 존재한 resident에 death... 세팅되어 있는 상태라고 가정하여 report를 생성함
     *
     * @param serialNumber
     * @param reportRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> registerBirthReport(@PathVariable("serialNumber") String serialNumber, @RequestBody BirthDeathReportRegisterRequest reportRequest){
        Resident deadResident = residentRepository.findByResidentSerialNumber(reportRequest.getResidentSerialNumber());
        BirthDeathReportResident deathReportResident = new BirthDeathReportResident();
        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
                reportRequest.getBirthDeathTypeCode(),
                Integer.valueOf(serialNumber),
                reportRequest.getResidentSerialNumber()
        );
        deathReportResident.setPk(pk);
        deathReportResident.setBirthDeathReportDate(reportRequest.getParsedReportDate());
        deathReportResident.setDeathReportQualificationCode(reportRequest.getDeathReportQualificationCode());
        if (reportRequest.getEmailAddress() != null){
            deathReportResident.setEmailAddress(reportRequest.getEmailAddress());
        }
        deathReportResident.setPhoneNumber(reportRequest.getPhoneNumber());
        deathReportResident.setResident(deadResident);
        birthDeathReportResidentRepository.save(deathReportResident);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{targetSerialNumber}")
    public ResponseEntity<Void> modifyBirthReport(@PathVariable("serialNumber") String serialNumber, @PathVariable("targetSerialNumber") String targetSerialNumber, @RequestBody BirthDeathReportModifyRequest reportRequest){
        BirthDeathReportResident birthDeathReportResident = birthDeathReportResidentRepository.findById(new BirthDeathReportResident.Pk(
                "사망",
                Integer.valueOf(serialNumber),
                Integer.valueOf(targetSerialNumber)
        )).get();

        if (reportRequest.getDeathReportQualificationCode() != null){
            birthDeathReportResident.setDeathReportQualificationCode(reportRequest.getDeathReportQualificationCode());
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
        // TypeCode는 항상 '사망'
        birthDeathReportResidentRepository.delete(birthDeathReportResidentRepository.findById(new BirthDeathReportResident.Pk(
                "사망",
                Integer.valueOf(serialNumber),
                Integer.valueOf(targetSerialNumber)
        )).get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
