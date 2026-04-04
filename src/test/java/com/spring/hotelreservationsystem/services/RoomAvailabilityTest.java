package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private RoomAvailabilityService roomAvailabilityService;

    private Room room1;
    private Room room2;

    @BeforeEach
    void setUp() {
        room1 = new Room("Single", 1, 50.0, "AVAILABLE");
        room1.setId(1L);

        room2 = new Room("Double", 2, 80.0, "AVAILABLE");
        room2.setId(2L);
    }

    @Test
    void shouldReturnAllRoomsWhenNoBookingsExist() {
        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));
        when(bookingRepository.findBookedRooms(any(), any())).thenReturn(List.of());

        List<Room> available = roomAvailabilityService.getAvailableRooms(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5)
        );

        assertThat(available).containsExactlyInAnyOrder(room1, room2);
    }

    @Test
    void shouldReturnOnlyAvailableRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));
        when(bookingRepository.findBookedRooms(any(), any())).thenReturn(List.of(room1));

        List<Room> available = roomAvailabilityService.getAvailableRooms(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5)
        );

        assertThat(available).containsExactly(room2);
        assertThat(available).doesNotContain(room1);
    }

    @Test
    void shouldReturnEmptyListWhenAllRoomsBooked() {
        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));
        when(bookingRepository.findBookedRooms(any(), any())).thenReturn(List.of(room1, room2));

        List<Room> available = roomAvailabilityService.getAvailableRooms(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5)
        );

        assertThat(available).isEmpty();
    }
}