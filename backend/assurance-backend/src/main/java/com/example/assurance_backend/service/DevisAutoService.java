package com.example.assurance_backend.service;

import com.example.assurance_backend.model.Contrat;
import com.example.assurance_backend.model.DevisAuto;
import com.example.assurance_backend.model.User;
import com.example.assurance_backend.repository.DevisAutoRepository;
import com.example.assurance_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DevisAutoService {

    @Autowired
    private DevisAutoRepository devisAutoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContratService contratService; 

    public DevisAuto saveDevisAuto(DevisAuto devisAuto, Long userId) throws Exception {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new Exception("Utilisateur non trouvé");
        }

        User user = userOpt.get();
        devisAuto.setUser(user);
        DevisAuto savedDevis = devisAutoRepository.save(devisAuto);

        Contrat contrat = new Contrat();
        contrat.setDevisAuto(savedDevis);
        contrat.setUser(user);
        contratService.saveContrat(contrat);

        return savedDevis;
    }

    public List<DevisAuto> getDevisAutoByUserId(Long userId) {
        return devisAutoRepository.findByUserId(userId);
    }
}
