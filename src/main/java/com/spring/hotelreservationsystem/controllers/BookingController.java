package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.BookingDTO;
import com.spring.hotelreservationsystem.mapper.BookingMapper;
import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET /bookings
    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings()
                .stream()
                .map(BookingMapper::toDTO)
                .toList();
    }

    // POST /bookings
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    // PUT /bookings/{id}
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    // DELETE /bookings/{id}
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
