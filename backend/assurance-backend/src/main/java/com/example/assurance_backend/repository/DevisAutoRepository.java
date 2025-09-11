package com.example.assurance_backend.repository;

import com.example.assurance_backend.model.DevisAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevisAutoRepository extends JpaRepository<DevisAuto, Long> {
    List<DevisAuto> findByUserId(Long userId);
}
