// src/test/java/com/spring/hotelreservationsystem/dto/LoginRequestDTOTest.java

package com.spring.hotelreservationsystem.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    @Test
    void gettersAndSetters() {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("test@mail.com");
        dto.setPassword("123");

        assertEquals("test@mail.com", dto.getEmail());
        assertEquals("123", dto.getPassword());
    }
}