package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.dto.BookingRequestDTO;
import com.spring.hotelreservationsystem.models.BookingStatus;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BookingService bookingService;

    private User user;
    private Room room;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("john@example.com");

        room = new Room("Single", 1, 100.0, "AVAILABLE");
        room.setId(1L);
    }

    @Test
    void shouldCreateBookingSuccessfully() {
        BookingRequestDTO dto = new BookingRequestDTO(1L, 1L,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var savedBooking = bookingService.createBooking(dto);

        assertNotNull(savedBooking);
        assertEquals(BookingStatus.CONFIRMED, savedBooking.getStatus());
        verify(bookingRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCheckOutBeforeCheckIn() {
        BookingRequestDTO dto = new BookingRequestDTO(1L, 1L,
                LocalDate.now().plusDays(3), LocalDate.now().plusDays(1)); // invalid

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.createBooking(dto));
    }
}