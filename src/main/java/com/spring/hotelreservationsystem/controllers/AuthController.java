package com.spring.hotelreservationsystem.controllers;


import com.spring.hotelreservationsystem.services.AuthService;
import com.spring.hotelreservationsystem.models.User;
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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }
//    @PostMapping("/login")
//    public String login(){
//        return authService.login();
//    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.login(user.getEmail(), user.getPassword()));
    }

}
