package com.spring.hotelreservationsystem.mapper;

import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.dto.RoomCreateDTO;
import com.spring.hotelreservationsystem.models.Room;

public class RoomMapper {

    public static RoomDTO toDTO(Room room) {
        if (room == null) return null;

        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoomType(room.getRoomType());
        dto.setBeds(room.getBeds());
        dto.setPrice(room.getPrice());
        dto.setStatus(room.getStatus());

        return dto;
    }

    public static Room toEntity(RoomCreateDTO dto) {
        if (dto == null) return null;

        Room room = new Room();
        room.setRoomType(dto.getRoomType());
        room.setBeds(dto.getBeds());
        room.setPrice(dto.getPrice());
        room.setStatus(dto.getStatus());

        return room;
    }
}