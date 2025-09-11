package com.example.assurance_backend.service;

import com.example.assurance_backend.model.Contrat;
import com.example.assurance_backend.repository.ContratRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ContratService {

    @Autowired
    private ContratRepository contratRepository;

    // ✅ Méthode manquante ajoutée
    public Contrat saveContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    public List<Contrat> getContratsByUserId(Long userId) {
        return contratRepository.findByUserId(userId);
    }

    public boolean isContratBelongsToUser(Long contratId, Long userId) {
        return contratRepository.findById(contratId)
            .map(contrat -> contrat.getUser() != null && contrat.getUser().getId().equals(userId))
            .orElse(false);
    }

    public byte[] generateContratPdf(Long contratId) {
        Contrat contrat = contratRepository.findById(contratId).orElse(null);
        if (contrat == null) {
            return null;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();
            document.add(new Paragraph("Contrat n°" + contrat.getId()));
            if (contrat.getUser() != null) {
                document.add(new Paragraph("Client : " + contrat.getUser().getUsername()));
            }
            document.add(new Paragraph("Date de création : " + contrat.getDateCreation()));
            document.add(new Paragraph("Statut : " + contrat.getStatut()));
            if (contrat.getDevisAuto() != null) {
                document.add(new Paragraph("Devis Auto ID : " + contrat.getDevisAuto().getId()));
            }
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
