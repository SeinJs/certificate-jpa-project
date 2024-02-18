package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.entity.Household;
import com.nhnacademy.jpa.entity.HouseholdCompositionResident;
import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.HouseholdCompositionResidentRepository;
import com.nhnacademy.jpa.repository.HouseholdRepository;
import com.nhnacademy.jpa.repository.ResidentRepository;
import com.nhnacademy.jpa.request.HouseholdRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/household")
public class HouseholdRestController {
    private final HouseholdRepository householdRepository;
    private final ResidentRepository residentRepository;
    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;

    public HouseholdRestController(HouseholdRepository householdRepository, ResidentRepository residentRepository, HouseholdCompositionResidentRepository householdCompositionResidentRepository) {
        this.householdRepository = householdRepository;
        this.residentRepository = residentRepository;
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
    }

    @PostMapping
    public ResponseEntity<Void> registerHousehold(@Valid @RequestBody HouseholdRegisterRequest registerRequest) {
        //household
        Household household = new Household();
        Resident baseResident = residentRepository.findByResidentSerialNumber(registerRequest.getResidentSerialNumber());

        // 세대주 중복 확인
        if (householdRepository.existsByResident_ResidentSerialNumber(registerRequest.getResidentSerialNumber())) {
            return ResponseEntity.badRequest().build();
        }

        //household composition
        List<HouseholdCompositionResident> householdCompositionResidentList = registerRequest.getHouseholdCompositionResidentList();
        if (householdCompositionResidentList != null){
            householdCompositionResidentRepository.saveAll(householdCompositionResidentList);
        }

        household.setHouseholdSerialNumber(householdRepository.getLastSerialNumber() + 1);
        household.setResident(baseResident);
        household.setHouseholdCompositionDate(registerRequest.getParsedCompositionDate());
        household.setHouseholdCompositionReasonCode(registerRequest.getHouseholdCompositionReasonCode());
        household.setCurrentHouseMovementAddress(registerRequest.getCurrentHouseMovementAddress());

        householdRepository.save(household);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{householdSerialNumber}")
    public ResponseEntity<Household> modifyHousehold(@PathVariable int householdSerialNumber) {
        Household household = householdRepository.findById(householdSerialNumber).get();
        List<HouseholdCompositionResident> householdCompositionResidentList = householdRepository.getAllByHouseholdSerialNumber(householdSerialNumber);

        if (householdCompositionResidentList.size() < 2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        householdRepository.deleteById(householdSerialNumber);

        // 배우자->본인 & 본인->배우자
        HouseholdCompositionResident spouse = householdCompositionResidentList.stream().filter(r -> r.getHouseholdRelationshipCode().equals("배우자")).findFirst().get();
        spouse.setHouseholdRelationshipCode("본인");

        HouseholdCompositionResident self = householdCompositionResidentList.stream().filter(r -> r.getHouseholdRelationshipCode().equals("본인")).findFirst().get();
        self.setHouseholdRelationshipCode("배우자");

        household.setResident(spouse.getResident());

        householdRepository.save(household);

        return ResponseEntity.ok().body(household);
    }

    @DeleteMapping("/{householdSerialNumber}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable("householdSerialNumber") int householdSerialNumber) {
        householdRepository.deleteById(householdSerialNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
