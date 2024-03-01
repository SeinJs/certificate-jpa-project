package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ResidentRepositoryImpl extends QuerydslRepositorySupport implements ResidentRepositoryCustom {
    public ResidentRepositoryImpl() {
        super(Resident.class);
    }

    @Override
    public List<FamilyRelationship> getResidentsRelationships(Integer serialNumber) {
//        return null;
        QResident resident = QResident.resident;
        QFamilyRelationship familyRelationship = QFamilyRelationship.familyRelationship;

        return from(resident)
                .innerJoin(familyRelationship.baseResident, resident)
                .where(resident.residentSerialNumber.eq(serialNumber))
                .select(familyRelationship)
                .fetch();
    }
}
