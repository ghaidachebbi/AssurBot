package com.example.assurance_backend.service;

import com.example.assurance_backend.model.Sinistre;
import com.example.assurance_backend.repository.SinistreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SinistreService {
    
    @Autowired
    private SinistreRepository sinistreRepository;

    public Sinistre enregistrerSinistre(Sinistre sinistre) {
        return sinistreRepository.save(sinistre);
    }
}
