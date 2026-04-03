package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.RoomCreateDTO;
import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.mapper.RoomMapper;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.services.RoomService;
import com.spring.hotelreservationsystem.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @Autowired
    public ManagerController(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    // ==================== ROOM MANAGEMENT ====================

    @GetMapping("/rooms")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms()
                .stream()
                .map(RoomMapper::toDTO)
                .toList();
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomCreateDTO dto) {
        Room room = RoomMapper.toEntity(dto);
        Room created = roomService.createRoom(room);
        return ResponseEntity.ok(RoomMapper.toDTO(created));
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomCreateDTO dto) {
        Room room = RoomMapper.toEntity(dto);
        Room updated = roomService.updateRoom(id, room);
        return ResponseEntity.ok(RoomMapper.toDTO(updated));
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== BOOKING MANAGEMENT ====================

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/bookings/date-range")
    public List<Booking> getBookingsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return bookingService.getAllBookings()
                .stream()
                .filter(booking -> !booking.getCheckInDate().isBefore(startDate) &&
                        !booking.getCheckOutDate().isAfter(endDate))
                .toList();
    }

    // ==================== DASHBOARD STATISTICS ====================

    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        List<Room> allRooms = roomService.getAllRooms();
        List<Booking> allBookings = bookingService.getAllBookings();

        long availableRooms = allRooms.stream()
                .filter(r -> "AVAILABLE".equalsIgnoreCase(r.getStatus()))
                .count();

        long occupiedRooms = allRooms.size() - availableRooms;

        long confirmedBookings = allBookings.stream()
                .filter(b -> "CONFIRMED".equalsIgnoreCase(b.getStatus().toString()))
                .count();

        stats.put("totalRooms", allRooms.size());
        stats.put("availableRooms", availableRooms);
        stats.put("occupiedRooms", occupiedRooms);
        stats.put("totalBookings", allBookings.size());
        stats.put("confirmedBookings", confirmedBookings);
        stats.put("occupancyRate", allRooms.isEmpty() ? 0 :
                (double) occupiedRooms / allRooms.size() * 100);

        return ResponseEntity.ok(stats);
    }
}