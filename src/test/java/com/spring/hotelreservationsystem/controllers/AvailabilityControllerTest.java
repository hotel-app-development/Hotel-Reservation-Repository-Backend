package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.HotelReservationSystemApplication;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HotelReservationSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository.deleteAll();

        Room room1 = new Room("Single", 1, 50.0, "AVAILABLE");
        Room room2 = new Room("Double", 2, 80.0, "AVAILABLE");
        Room room3 = new Room("Suite", 3, 150.0, "OCCUPIED");

        roomRepository.saveAll(List.of(room1, room2, room3));
    }

    @Test
    void shouldReturnAvailableRoomsForValidDates() throws Exception {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(3);

        mockMvc.perform(get("/api/availability")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].status", everyItem(is("AVAILABLE"))));
    }

    @Test
    void shouldReturnEmptyListIfNoRoomsAvailable() throws Exception {
        roomRepository.findAll().forEach(room -> {
            room.setStatus("OCCUPIED");
            roomRepository.save(room);
        });

        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(2);

        mockMvc.perform(get("/api/availability")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnBadRequestForInvalidDateRange() throws Exception {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.minusDays(1);

        mockMvc.perform(get("/api/availability")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isBadRequest());
    }
}