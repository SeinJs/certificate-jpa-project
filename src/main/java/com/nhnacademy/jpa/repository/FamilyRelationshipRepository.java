package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, FamilyRelationship.Pk> {
    List<FamilyRelationship> findAllByBaseResident_ResidentSerialNumber(Integer serialNumber);
    FamilyRelationship findByBaseResident_ResidentSerialNumberAndFamilyRelationshipCode(Integer serialNumber, String relationshipCode);
}
