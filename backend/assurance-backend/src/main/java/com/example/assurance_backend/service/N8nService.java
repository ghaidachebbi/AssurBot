package com.example.assurance_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
@Service
public class N8nService {
    private static final Logger logger = LoggerFactory.getLogger(N8nService.class);
    private final RestTemplate restTemplate;
    private final String n8nWebhookUrl;

    public N8nService(RestTemplateBuilder builder,
                    @Value("${n8n.webhook.url}") String n8nWebhookUrl) {
        this.restTemplate = builder
            .setConnectTimeout(Duration.ofSeconds(15))
            .setReadTimeout(Duration.ofSeconds(30))
            .build();
        this.n8nWebhookUrl = n8nWebhookUrl;
        logger.info("N8nService configuré avec l'URL : {}", n8nWebhookUrl);
    }

    public Map<String, Object> sendToN8n(Map<String, Object> requestBody, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            logger.debug("Envoi à n8n - URL: {}, Body: {}", n8nWebhookUrl, requestBody);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                n8nWebhookUrl,
                HttpMethod.POST,
                entity,
                Map.class
            );

            logger.debug("Réponse de n8n - Status: {}, Body: {}", 
                response.getStatusCode(), response.getBody());

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Erreur client HTTP - Status: {}, Response: {}", 
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erreur de communication avec n8n: " + e.getStatusCode());
        } catch (HttpServerErrorException e) {
            logger.error("Erreur serveur HTTP - Status: {}, Response: {}", 
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erreur serveur n8n: " + e.getStatusCode());
        } catch (ResourceAccessException e) {
            logger.error("Timeout de connexion à n8n", e);
            throw new RuntimeException("Service temporairement indisponible");
        } catch (Exception e) {
            logger.error("Erreur inattendue", e);
            throw new RuntimeException("Erreur technique");
        }
    }
}