package com.edgar.carparking.repository;

import com.edgar.carparking.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    Optional<Resident> findByUsername(String username);
}
