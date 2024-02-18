package com.nhnacademy.jpa.controller.restcontroller;

import com.nhnacademy.jpa.domain.ResidentDto;
import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.request.ResidentModifyRequest;
import com.nhnacademy.jpa.request.ResidentRegisterRequest;
import com.nhnacademy.jpa.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/residents")
public class ResidentRestController {
    private final ResidentService residentService;

    public ResidentRestController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping
    public ResponseEntity<Resident> registerResident(@Valid @RequestBody ResidentRegisterRequest registerRequest){
        Resident resident = residentService.createResident(residentService.toResident(registerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(resident);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<ResidentDto> updateResident(@PathVariable("serialNumber") Integer serialNumber, @Valid @RequestBody ResidentModifyRequest modifyRequest){
        return ResponseEntity.ok().body(residentService.modifyResident(residentService.toResident(serialNumber, modifyRequest)));
    }
}
