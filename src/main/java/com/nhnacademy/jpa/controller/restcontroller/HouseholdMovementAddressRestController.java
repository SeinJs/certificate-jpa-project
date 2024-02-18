package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.entity.Household;
import com.nhnacademy.jpa.entity.HouseholdMovementAddress;
import com.nhnacademy.jpa.repository.HouseholdMovementAddressRepository;
import com.nhnacademy.jpa.repository.HouseholdRepository;
import com.nhnacademy.jpa.request.HouseholdMovementAddressModifyRequest;
import com.nhnacademy.jpa.request.HouseholdMovementAddressRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/household/{householdSerialNumber}/movement")
public class HouseholdMovementAddressRestController {
    private final HouseholdMovementAddressRepository movementAddressRepository;
    private final HouseholdRepository householdRepository;

    public HouseholdMovementAddressRestController(HouseholdMovementAddressRepository movementAddressRepository, HouseholdRepository householdRepository) {
        this.movementAddressRepository = movementAddressRepository;
        this.householdRepository = householdRepository;
    }

    @PostMapping
    public ResponseEntity<Void> registerMovement(@PathVariable("householdSerialNumber") int householdSerialNumber, @RequestBody HouseholdMovementAddressRegisterRequest movementAddressRequest){
        Household household = householdRepository.findById(householdSerialNumber).get();
        HouseholdMovementAddress newAddressMovement = new HouseholdMovementAddress();
        HouseholdMovementAddress.Pk pk = new HouseholdMovementAddress.Pk(
                householdSerialNumber,
                movementAddressRequest.getHouseMovementReportDate()
        );
        newAddressMovement.setPk(pk);
        newAddressMovement.setHouseMovementAddress(movementAddressRequest.getHouseMovementAddress());
        newAddressMovement.setLastAddressYn("Y");
        newAddressMovement.setHousehold(household);
        movementAddressRepository.changeLatestToN();
        movementAddressRepository.save(newAddressMovement);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{reportDate}")
    public ResponseEntity<Void> modifyMovement(@PathVariable("householdSerialNumber") int householdSerialNumber, @PathVariable("reportDate") String reportDate, @RequestBody HouseholdMovementAddressModifyRequest modifyRequest){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedReportDate = dateFormat.parse(reportDate);
            HouseholdMovementAddress movementAddress = movementAddressRepository.findById(new HouseholdMovementAddress.Pk(householdSerialNumber, parsedReportDate)).get();

            if (modifyRequest.getHouseMovementAddress() != null){
                movementAddress.setHouseMovementAddress(modifyRequest.getHouseMovementAddress());
            }

            if (modifyRequest.getLastAddressYn() != null){
                movementAddress.setLastAddressYn(modifyRequest.getLastAddressYn());
            }

            movementAddressRepository.save(movementAddress);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportDate}")
    public ResponseEntity<Void> deleteMovement(@PathVariable("householdSerialNumber") int householdSerialNumber, @PathVariable("reportDate") String reportDate){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedReportDate = dateFormat.parse(reportDate);

            movementAddressRepository.deleteById(new HouseholdMovementAddress.Pk(householdSerialNumber, parsedReportDate));
        }catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.accepted().build();
    }
}
