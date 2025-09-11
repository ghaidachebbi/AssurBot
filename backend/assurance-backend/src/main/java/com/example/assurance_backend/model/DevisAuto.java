package com.example.assurance_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "devis_auto")
public class DevisAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeVehicule;
    private String valeurNeuve;
    private String valeurActuelle;
    private String marque;
    private String modeleVeh;
    private String puissance;
    private String annee;
    private String immatriculation;
    private String usageVehicule;
    private String kilometrage;
    private String stationnement;
    private String dateAchat;
    private String credit;
    private String alarme;

    private String nom;
    private String prenom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String telephone;
    private String email;


    @ManyToOne(fetch = FetchType.EAGER)  
    @JsonIgnore                          
    @JoinColumn(name = "user_id")
    private User user;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTypeVehicule() { return typeVehicule; }
    public void setTypeVehicule(String typeVehicule) { this.typeVehicule = typeVehicule; }

    public String getValeurNeuve() { return valeurNeuve; }
    public void setValeurNeuve(String valeurNeuve) { this.valeurNeuve = valeurNeuve; }

    public String getValeurActuelle() { return valeurActuelle; }
    public void setValeurActuelle(String valeurActuelle) { this.valeurActuelle = valeurActuelle; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModeleVeh() { return modeleVeh; }
    public void setModeleVeh(String modeleVeh) { this.modeleVeh = modeleVeh; }

    public String getPuissance() { return puissance; }
    public void setPuissance(String puissance) { this.puissance = puissance; }

    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public String getUsageVehicule() { return usageVehicule; }
    public void setUsageVehicule(String usageVehicule) { this.usageVehicule = usageVehicule; }

    public String getKilometrage() { return kilometrage; }
    public void setKilometrage(String kilometrage) { this.kilometrage = kilometrage; }

    public String getStationnement() { return stationnement; }
    public void setStationnement(String stationnement) { this.stationnement = stationnement; }

    public String getDateAchat() { return dateAchat; }
    public void setDateAchat(String dateAchat) { this.dateAchat = dateAchat; }

    public String getCredit() { return credit; }
    public void setCredit(String credit) { this.credit = credit; }

    public String getAlarme() { return alarme; }
    public void setAlarme(String alarme) { this.alarme = alarme; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
