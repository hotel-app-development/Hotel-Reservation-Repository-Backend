package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomAvailabilityService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomAvailabilityService(RoomRepository roomRepository,
                                   BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Room> getAvailableRooms(LocalDate start, LocalDate end) {
        List<Room> allRooms = roomRepository.findAll();
        List<Room> bookedRooms = bookingRepository.findBookedRooms(start, end);

        return allRooms.stream()
                .filter(room -> "AVAILABLE".equalsIgnoreCase(room.getStatus()))
                .filter(room -> !bookedRooms.contains(room))
                .toList();
    }
}