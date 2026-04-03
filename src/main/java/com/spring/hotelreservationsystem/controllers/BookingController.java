package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.BookingDTO;
import com.spring.hotelreservationsystem.dto.BookingRequestDTO;
import com.spring.hotelreservationsystem.dto.BookingResponseDTO;
import com.spring.hotelreservationsystem.mapper.BookingMapper;
import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings()
                .stream()
                .map(BookingMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        Booking saved = bookingService.createBooking(request);
        BookingResponseDTO response = new BookingResponseDTO(
                saved.getId(),
                "Booking created successfully"
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Long id,
            @RequestBody BookingRequestDTO request
    ) {
        Booking updated = bookingService.updateBooking(id, request);
        return ResponseEntity.ok(BookingMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}