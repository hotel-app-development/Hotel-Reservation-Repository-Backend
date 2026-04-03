package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.RoomCreateDTO;
import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.mapper.RoomMapper;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    //GET /rooms
    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms()
                .stream()
                .map(RoomMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomCreateDTO dto) {

        Room room = RoomMapper.toEntity(dto);
        Room created = roomService.createRoom(room);

        return ResponseEntity.ok(RoomMapper.toDTO(created));
    }

    //PUT /rooms/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Room updated = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updated);
    }

    //DELETE /rooms/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}