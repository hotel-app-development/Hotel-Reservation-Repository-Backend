package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.LoginRequestDTO;
import com.spring.hotelreservationsystem.dto.LoginResponseDTO;
import com.spring.hotelreservationsystem.dto.UserDTO;
import com.spring.hotelreservationsystem.dto.UserRegistrationDTO;
import com.spring.hotelreservationsystem.mapper.UserMapper;
import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO dto) {

        User user = UserMapper.toEntity(dto);
        User saved = authService.register(user);

        return ResponseEntity.ok(UserMapper.toDTO(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(
                authService.login(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    }
}