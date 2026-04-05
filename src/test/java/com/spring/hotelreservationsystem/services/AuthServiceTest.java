// src/test/java/com/spring/hotelreservationsystem/services/AuthServiceTest.java

package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.dto.LoginResponseDTO;
import com.spring.hotelreservationsystem.models.Role;
import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import com.spring.hotelreservationsystem.security.JwtUtil;
import com.spring.hotelreservationsystem.security.UserDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailService userDetailService;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_success() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("hashed");
        user.setRole(Role.MANAGER);

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("pass", "hashed"))
                .thenReturn(true);

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        "test@mail.com", "hashed", List.of());

        when(userDetailService.loadUserByUsername("test@mail.com"))
                .thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails))
                .thenReturn("fake-jwt");

        LoginResponseDTO response = authService.login("test@mail.com", "pass");

        assertEquals("fake-jwt", response.getToken());
        assertEquals("MANAGER", response.getRole());
    }

    @Test
    void login_invalidPassword() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("hashed");

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> authService.login("test@mail.com", "wrong"));
    }
}