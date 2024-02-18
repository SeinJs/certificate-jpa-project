package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.FamilyRelationship;
import com.nhnacademy.jpa.entity.QFamilyRelationship;
import com.nhnacademy.jpa.entity.QResident;
import com.nhnacademy.jpa.entity.Resident;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ResidentRepositoryImpl extends QuerydslRepositorySupport implements ResidentRepositoryCustom {
    public ResidentRepositoryImpl() {
        super(Resident.class);
    }

    @Override
    public List<FamilyRelationship> getResidentsRelationships(Integer serialNumber) {
        QResident resident = QResident.resident;
        QFamilyRelationship familyRelationship = QFamilyRelationship.familyRelationship;

        return from(resident)
                .innerJoin(resident.baseRelationshipList, familyRelationship)
                .where(resident.residentSerialNumber.eq(serialNumber))
                .select(familyRelationship)
                .fetch();
    }
}
