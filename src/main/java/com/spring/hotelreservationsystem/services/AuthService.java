package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

//    public String login() {
//        return "Login successful";
//    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(password, user.getPassword())){
            return "Login successful";
        }

        throw new RuntimeException("Invalid password");
    }
}
