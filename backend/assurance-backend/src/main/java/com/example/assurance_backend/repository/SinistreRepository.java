package com.example.assurance_backend.repository;

import com.example.assurance_backend.model.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinistreRepository extends JpaRepository<Sinistre, Long> {
}
