package com.example.assurance_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contrats")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreation = LocalDateTime.now();

    private String statut = "EN_ATTENTE";

    @OneToOne
    @JoinColumn(name = "devis_auto_id")
    private DevisAuto devisAuto;

    @ManyToOne(fetch = FetchType.EAGER)  
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("contrats")
    private User user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public DevisAuto getDevisAuto() { return devisAuto; }
    public void setDevisAuto(DevisAuto devisAuto) { this.devisAuto = devisAuto; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}