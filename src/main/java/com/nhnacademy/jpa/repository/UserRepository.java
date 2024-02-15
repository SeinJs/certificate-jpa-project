package com.nhnacademy.jpa.repository;

import com.nhnacademy.jpa.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Resident, String> {
}
