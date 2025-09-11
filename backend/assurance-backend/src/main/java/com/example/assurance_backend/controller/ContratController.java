package com.example.assurance_backend.controller;

import com.example.assurance_backend.security.UserDetailsImpl;
import com.example.assurance_backend.service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.assurance_backend.model.Contrat;

@RestController
@RequestMapping("/api/contrats")
@CrossOrigin(origins = "http://localhost:3000")
public class ContratController {

    @Autowired
    private ContratService contratService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getContratsByUser(@PathVariable Long userId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(userId)) {
            return ResponseEntity.status(403).body("Accès refusé");
        }

        try {
            List<Contrat> contrats = contratService.getContratsByUserId(userId);
            return ResponseEntity.ok(contrats);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }

    @GetMapping("/{contratId}/pdf")
    public ResponseEntity<byte[]> downloadContratPdf(@PathVariable Long contratId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!contratService.isContratBelongsToUser(contratId, userDetails.getId())) {
            return ResponseEntity.status(403).build();
        }

        try {
            byte[] pdfBytes = contratService.generateContratPdf(contratId);

            if (pdfBytes == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=contrat-" + contratId + ".pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
