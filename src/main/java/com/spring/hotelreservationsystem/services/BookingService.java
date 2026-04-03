package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.dto.BookingRequestDTO;
import com.spring.hotelreservationsystem.mapper.BookingMapper;
import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.BookingStatus;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Booking createBooking(BookingRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Booking booking = BookingMapper.toEntity(dto, user, room);
        validateBooking(booking);

        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, BookingRequestDTO dto) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        existing.setUser(user);
        existing.setRoom(room);
        existing.setCheckInDate(dto.getCheckInDate());
        existing.setCheckOutDate(dto.getCheckOutDate());

        validateBooking(existing);
        return bookingRepository.save(existing);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    private void validateBooking(Booking booking) {
        if (booking.getUser() == null || booking.getRoom() == null) {
            throw new IllegalArgumentException("User and Room are required");
        }
        if (booking.getCheckInDate() == null || booking.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }
        if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
    }
}