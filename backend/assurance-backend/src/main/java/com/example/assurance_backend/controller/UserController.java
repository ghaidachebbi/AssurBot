package com.example.assurance_backend.controller;

import com.example.assurance_backend.model.User;
import com.example.assurance_backend.security.UserDetailsImpl;
import com.example.assurance_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Non authentifié");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        try {
            User user = userService.findById(userDetails.getId());

            Map<String, Object> response = Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "contrats", user.getContrats() 
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }
    }
}
