package com.example.assurance_backend.controller;

import com.example.assurance_backend.security.UserDetailsImpl;
import com.example.assurance_backend.service.N8nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatbotController {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotController.class);

    private final N8nService n8nService;

    public ChatbotController(N8nService n8nService) {
        this.n8nService = n8nService;
    }

    @PostMapping("/process")
    public ResponseEntity<?> processMessage(
            @RequestBody Map<String, Object> requestBody,
            Authentication authentication) {
        
        logger.debug("Requête reçue sur /api/chatbot/process avec le corps: {}", requestBody);
        
        // Validation de l'authentification
        ResponseEntity<?> authError = validateAuthentication(authentication);
        if (authError != null) return authError;

        // Validation du corps de la requête
        if (requestBody == null || !requestBody.containsKey("message")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("reply", "Le champ 'message' est requis"));
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String userId = String.valueOf(userDetails.getId());
        String token = (String) requestBody.getOrDefault("token", "");
        
        requestBody.put("userId", userId);
        requestBody.put("token", token);

        try {
            logger.info("Traitement du message du chatbot pour l'utilisateur ID: {}", userId);
            Map<String, Object> response = n8nService.sendToN8n(requestBody, token);
            logger.debug("Réponse de n8n pour /process: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erreur lors du traitement du message pour l'utilisateur ID {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("reply", "Erreur serveur: " + e.getMessage()));
        }
    }

    @PostMapping("/sinistre")
    public ResponseEntity<?> declareSinistre(
            @RequestBody Map<String, Object> requestBody,
            Authentication authentication) {
        
        logger.debug("Requête reçue sur /api/chatbot/sinistre avec le corps: {}", requestBody);
        
        // Validation de l'authentification
        ResponseEntity<?> authError = validateAuthentication(authentication);
        if (authError != null) return authError;

        // Validation des champs requis
        String[] requiredFields = {"nom", "prenom", "email", "telephone", "type", "description"};
        for (String field : requiredFields) {
            if (!requestBody.containsKey(field)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("reply", "Le champ '" + field + "' est requis"));
            }
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String userId = String.valueOf(userDetails.getId());
        String token = (String) requestBody.getOrDefault("token", "");
        
        requestBody.put("userId", userId);
        requestBody.put("token", token);

        try {
            logger.info("Déclaration de sinistre pour l'utilisateur ID: {}", userId);
            Map<String, Object> response = n8nService.sendToN8n(requestBody, token);
            logger.debug("Réponse de n8n pour /sinistre: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erreur lors de la déclaration de sinistre pour l'utilisateur ID {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("reply", "Erreur serveur: " + e.getMessage()));
        }
    }

    private ResponseEntity<?> validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            logger.warn("Tentative d'accès non authentifiée");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("reply", "Authentification requise"));
        }

        if (!(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            logger.error("Principal invalide: {}", authentication.getPrincipal().getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("reply", "Erreur serveur: configuration d'authentification invalide"));
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getId() == null) {
            logger.error("ID utilisateur manquant pour: {}", userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("reply", "Erreur serveur: ID utilisateur manquant"));
        }

        return null;
    }
}