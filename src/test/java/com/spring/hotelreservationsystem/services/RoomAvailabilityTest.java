package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RoomAvailabilityTest {

    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private RoomAvailabilityService roomAvailabilityService;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        bookingRepository = mock(BookingRepository.class);

        roomAvailabilityService =
                new RoomAvailabilityService(roomRepository, bookingRepository);
    }

    // Scenario 1 — No bookings
    @Test
    void shouldReturnAllRoomsWhenNoBookingsExist() {

        Room room1 = new Room();
        room1.setId(1L);

        Room room2 = new Room();
        room2.setId(2L);

        when(roomRepository.findAll())
                .thenReturn(List.of(room1, room2));

        when(bookingRepository.findBookedRooms(any(), any()))
                .thenReturn(List.of());

        List<Room> available =
                roomAvailabilityService.getAvailableRooms(
                        LocalDate.now(),
                        LocalDate.now().plusDays(5)
                );

        assertThat(available).contains(room1, room2);
    }

    // Scenario 2 — Some bookings
    @Test
    void shouldReturnOnlyAvailableRooms() {

        Room room1 = new Room();
        room1.setId(1L);

        Room room2 = new Room();
        room2.setId(2L);

        when(roomRepository.findAll())
                .thenReturn(List.of(room1, room2));

        when(bookingRepository.findBookedRooms(any(), any()))
                .thenReturn(List.of(room1));

        List<Room> available =
                roomAvailabilityService.getAvailableRooms(
                        LocalDate.now(),
                        LocalDate.now().plusDays(5)
                );

        assertThat(available).contains(room2);
        assertThat(available).doesNotContain(room1);
    }

    // Scenario 3 — Overlapping bookings
    @Test
    void shouldReturnEmptyListWhenAllRoomsBooked() {

        Room room1 = new Room();
        room1.setId(1L);

        Room room2 = new Room();
        room2.setId(2L);

        when(roomRepository.findAll())
                .thenReturn(List.of(room1, room2));

        when(bookingRepository.findBookedRooms(any(), any()))
                .thenReturn(List.of(room1, room2));

        List<Room> available =
                roomAvailabilityService.getAvailableRooms(
                        LocalDate.now(),
                        LocalDate.now().plusDays(5)
                );

        assertThat(available).isEmpty();
    }
}