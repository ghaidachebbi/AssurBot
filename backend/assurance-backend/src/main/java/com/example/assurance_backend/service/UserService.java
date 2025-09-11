package com.example.assurance_backend.service;

import com.example.assurance_backend.model.User;
import com.example.assurance_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Nom d'utilisateur déjà pris");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email déjà utilisé");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("CLIENT");
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) throws Exception {
        return userRepository.findById(id)
            .orElseThrow(() -> new Exception("Utilisateur non trouvé"));
    }
}
