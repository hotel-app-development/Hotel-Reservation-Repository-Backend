package com.spring.hotelreservationsystem.services;


import com.spring.hotelreservationsystem.services.AuthService;
import com.spring.hotelreservationsystem.models.Role;
import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldEncodePassword_AndSaveUser(){

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@gmail.com");
        user.setPassword("123456");
        user.setRole(Role.CUSTOMER);

        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = authService.register(user);

        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(user);

        assertEquals("encodedPassword", user.getPassword());
    }



    @Test
    void login_ShouldReturnSuccess_WhenPasswordMatches() {

        User user = new User();
        user.setEmail("john@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);

        String result = authService.login("john@gmail.com", "123456");

        assertEquals("Login successful", result);
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIncorrect() {

        User user = new User();
        user.setEmail("john@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encodedPassword")).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login("john@gmail.com", "wrongpass");
        });

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {

        when(userRepository.findByEmail("unknown@gmail.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login("unknown@gmail.com", "123456");
        });

        assertEquals("User not found", exception.getMessage());
    }
}
