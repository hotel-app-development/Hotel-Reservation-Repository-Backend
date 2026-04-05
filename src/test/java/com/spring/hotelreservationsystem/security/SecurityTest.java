package com.spring.hotelreservationsystem.security;

import com.spring.hotelreservationsystem.controllers.ManagerController;
import com.spring.hotelreservationsystem.services.AuthService;
import com.spring.hotelreservationsystem.services.BookingService;
import com.spring.hotelreservationsystem.services.RoomAvailabilityService;
import com.spring.hotelreservationsystem.services.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerController.class)
class SecurityTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomAvailabilityService roomAvailabilityService;
    @MockBean
    private AuthService authService;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private RoomService roomService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void managerEndpoint_requiresAuth() throws Exception {
        mockMvc.perform(get("/api/manager/rooms"))
                .andExpect(status().isUnauthorized());
    }
}
