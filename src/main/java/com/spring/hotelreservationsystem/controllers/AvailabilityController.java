package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.mapper.RoomMapper;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.services.RoomAvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final RoomAvailabilityService roomAvailabilityService;

    public AvailabilityController(RoomAvailabilityService roomAvailabilityService) {
        this.roomAvailabilityService = roomAvailabilityService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end
    ) {
        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().build();
        }

        List<Room> availableRooms = roomAvailabilityService.getAvailableRooms(start, end);

        List<RoomDTO> dtos = availableRooms.stream()
                .map(RoomMapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}