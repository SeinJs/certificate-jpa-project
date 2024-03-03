package com.nhnacademy.jpa.service;

import com.nhnacademy.jpa.domain.ResidentDto;
import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.ResidentRepository;
import com.nhnacademy.jpa.request.ResidentModifyRequest;
import com.nhnacademy.jpa.request.ResidentRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResidentService {
    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public Resident getResident(Integer serialNumber){
        return residentRepository.findByResidentSerialNumber(serialNumber);
    }

    @Transactional
    public Resident createResident(Resident resident){
        if (residentRepository.existsByResidentSerialNumber(resident.getResidentSerialNumber()) ||
                residentRepository.existsByResidentRegistrationNumber(resident.getResidentRegistrationNumber())){
            throw new RuntimeException("Duplicate Resident Error");
        }
        return residentRepository.save(resident);
    }

    @Transactional
    public ResidentDto modifyResident(Resident resident){
        residentRepository.save(resident);

        return residentRepository.findByResidentRegistrationNumber(resident.getResidentRegistrationNumber());
    }

    /** toResident는 Resident 반환한다
     *
     * @param residentRegisterRequest
     * @return resident
     */
    public Resident toResident(ResidentRegisterRequest residentRegisterRequest){
        Resident resident = new Resident();
        resident.setResidentSerialNumber(residentRegisterRequest.getResidentSerialNumber());
        resident.setName(residentRegisterRequest.getName());
        resident.setResidentRegistrationNumber(residentRegisterRequest.getResidentRegistrationNumber());
        resident.setGenderCode(residentRegisterRequest.getGenderCode());
        resident.setBirthDate(residentRegisterRequest.getParsedBirthDate());
        resident.setBirthPlaceCode(residentRegisterRequest.getBirthPlaceCode());
        resident.setRegistrationBaseAddress(residentRegisterRequest.getRegistrationBaseAddress());

        if (residentRegisterRequest.getDeathDate() != null){
            resident.setDeathDate(residentRegisterRequest.getParsedDeathDate());
            resident.setDeathPlaceCode(residentRegisterRequest.getDeathPlaceCode());
            resident.setDeathPlaceAddress(residentRegisterRequest.getDeathPlaceAddress());
        }

        if (residentRegisterRequest.getId() != null){
            resident.setId(residentRegisterRequest.getId());
            resident.setPassword(residentRegisterRequest.getPassword());
            resident.setEmail(residentRegisterRequest.getEmail());
        }

        return resident;
    }

    public Resident toResident(Integer serialNumber, ResidentModifyRequest residentModifyRequest){
        Resident resident = getResident(serialNumber);
        if (residentModifyRequest.getName() != null){
            resident.setName(residentModifyRequest.getName());
            resident.setResidentRegistrationNumber(residentModifyRequest.getResidentRegistrationNumber());
            resident.setGenderCode(residentModifyRequest.getGenderCode());
        }

        if (residentModifyRequest.getDeathDate() != null){
            resident.setDeathDate(residentModifyRequest.getDeathDate());
            resident.setDeathPlaceCode(residentModifyRequest.getDeathPlaceCode());
            resident.setDeathPlaceAddress(residentModifyRequest.getDeathPlaceAddress());
        }

        if (residentModifyRequest.getId() != null){
            resident.setId(residentModifyRequest.getId());
            resident.setPassword(residentModifyRequest.getPassword());
            resident.setEmail(residentModifyRequest.getEmail());
        }

        return resident;
    }
}
