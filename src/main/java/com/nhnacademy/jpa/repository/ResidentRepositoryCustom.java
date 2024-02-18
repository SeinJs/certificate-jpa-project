package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.FamilyRelationship;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ResidentRepositoryCustom {
    List<FamilyRelationship> getResidentsRelationships(Integer serialNumber);
}
