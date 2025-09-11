package com.example.assurance_backend.controller;

import com.example.assurance_backend.model.DevisAuto;
import com.example.assurance_backend.security.UserDetailsImpl;
import com.example.assurance_backend.service.DevisAutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/devis-auto")
public class DevisAutoController {

    @Autowired
    private DevisAutoService devisAutoService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createDevisAuto(@PathVariable Long userId,
                                             @RequestBody DevisAuto devisAuto,
                                             Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.getId().equals(userId)) {
            return ResponseEntity.status(403).body("Accès refusé");
        }

        try {
            DevisAuto saved = devisAutoService.saveDevisAuto(devisAuto, userId);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getDevisAutoByUser(@PathVariable Long userId, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.getId().equals(userId)) {
            return ResponseEntity.status(403).body("Accès refusé");
        }

        List<DevisAuto> devisAutos = devisAutoService.getDevisAutoByUserId(userId);
        return ResponseEntity.ok(devisAutos);
    }
}
