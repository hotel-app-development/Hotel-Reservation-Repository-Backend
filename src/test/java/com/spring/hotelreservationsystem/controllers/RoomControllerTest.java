package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.HotelReservationSystemApplication;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HotelReservationSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository.deleteAll();
    }

    @Test
    void testCRUDOperations() throws Exception {
        // CREATE
        String roomJson = """
                {"roomType":"Single","beds":1,"price":50.0,"status":"AVAILABLE"}
                """;

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roomJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.roomType").value("Single"));

        Long roomId = roomRepository.findAll().get(0).getId();

        // READ ALL
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // UPDATE
        String updatedJson = """
                {"roomType":"Double","beds":2,"price":80.0,"status":"OCCUPIED"}
                """;

        mockMvc.perform(put("/api/rooms/" + roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beds").value(2))
                .andExpect(jsonPath("$.price").value(80.0));

        // DELETE
        mockMvc.perform(delete("/api/rooms/" + roomId))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}