package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.entity.FamilyRelationship;
import com.nhnacademy.jpa.repository.FamilyRelationshipRepository;
import com.nhnacademy.jpa.repository.ResidentRepository;
import com.nhnacademy.jpa.request.FamilyRelationshipRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/residents/{serialNumber}/relationship")
public class FamilyRelationshipRestController {
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final ResidentRepository residentRepository;

    public FamilyRelationshipRestController(FamilyRelationshipRepository familyRelationshipRepository, ResidentRepository residentRepository) {
        this.familyRelationshipRepository = familyRelationshipRepository;
        this.residentRepository = residentRepository;
    }

    @PostMapping
    public ResponseEntity<Void> registerFamilyRelationship(@Valid @RequestBody FamilyRelationshipRequest registerRequest, @PathVariable("serialNumber") String serialNumber) {
        FamilyRelationship familyRelationship = new FamilyRelationship();
        FamilyRelationship.Pk pk = new FamilyRelationship.Pk(Integer.valueOf(serialNumber), registerRequest.getFamilyResidentSerialNumber());
        familyRelationship.setPk(pk);
        familyRelationship.setFamilyRelationshipCode(registerRequest.getFamilyRelationshipCode());
        familyRelationship.setBaseResident(residentRepository.findByResidentSerialNumber(Integer.valueOf(serialNumber)));

        familyRelationshipRepository.save(familyRelationship);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{familySerialNumber}")
    public ResponseEntity<Void> modifyFamilyRelationship(@Valid @RequestBody FamilyRelationshipRequest modifyRequest, @PathVariable("serialNumber") Integer serialNumber, @PathVariable("familySerialNumber") Integer familySerialNumber) {
        FamilyRelationship familyRelationship = new FamilyRelationship();
        FamilyRelationship.Pk pk = new FamilyRelationship.Pk(serialNumber, familySerialNumber);
        familyRelationship.setPk(pk);
        familyRelationship.setFamilyRelationshipCode(modifyRequest.getFamilyRelationshipCode());
        familyRelationship.setBaseResident(residentRepository.findByResidentSerialNumber(serialNumber));

        familyRelationshipRepository.save(familyRelationship);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{familySerialNumber}")
    public ResponseEntity<Void> deleteFamilyRelationship(@PathVariable("serialNumber") Integer serialNumber, @PathVariable("familySerialNumber") Integer familySerialNumber){
        familyRelationshipRepository.delete(familyRelationshipRepository.findById(new FamilyRelationship.Pk(serialNumber, familySerialNumber)).get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
