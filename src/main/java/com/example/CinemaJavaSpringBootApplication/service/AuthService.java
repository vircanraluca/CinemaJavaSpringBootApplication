package com.example.CinemaJavaSpringBootApplication.service;

import com.example.CinemaJavaSpringBootApplication.dto.RegisterRequest;
import com.example.CinemaJavaSpringBootApplication.model.Role;
import com.example.CinemaJavaSpringBootApplication.model.User;
import com.example.CinemaJavaSpringBootApplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username-ul este deja folosit!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email-ul este deja înregistrat!");
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                Set.of(Role.ROLE_USER)
        );

        userRepository.save(user);
        return "Utilizator înregistrat cu succes!";
    }
}