package com.example.assurance_backend.controller;

import com.example.assurance_backend.model.Sinistre;
import java.util.Map;

import com.example.assurance_backend.model.User;
import com.example.assurance_backend.repository.UserRepository;
import com.example.assurance_backend.service.SinistreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/sinistres")
@CrossOrigin(origins = "http://localhost:3000")
public class SinistreController {

    @Autowired
    private SinistreService sinistreService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/declarer")
    public ResponseEntity<?> declarerSinistre(@RequestBody Sinistre sinistre, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Utilisateur non authentifié"));
        }
        Optional<User> userOpt = userRepository.findByEmail(userDetails.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Utilisateur non trouvé"));
        }
        sinistre.setUser(userOpt.get());
        sinistreService.enregistrerSinistre(sinistre);
        return ResponseEntity.ok(Map.of("message", "Sinistre enregistré avec succès"));
    }
}

