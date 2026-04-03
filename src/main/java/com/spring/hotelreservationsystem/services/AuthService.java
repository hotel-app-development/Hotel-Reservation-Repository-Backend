package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import com.spring.hotelreservationsystem.security.JwtUtil;
import com.spring.hotelreservationsystem.security.UserDetailService;
import com.spring.hotelreservationsystem.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    public User register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

//    public String login() {
//        return "Login successful";
//    }

    public LoginResponseDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        var userDetails = userDetailService.loadUserByUsername(email);
        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponseDTO(token, user.getRole().name()); // <-- include role
    }
}
