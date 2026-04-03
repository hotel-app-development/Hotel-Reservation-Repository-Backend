package com.spring.hotelreservationsystem;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//Class for generating a password for the manager, this manager should use the desired password
public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "manager123"; // your desired password
        String hash = encoder.encode(rawPassword);
        System.out.println("BCrypt hash: " + hash);
    }
}