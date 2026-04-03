package com.spring.hotelreservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AvailabilityResponseDTO {
    private List<RoomDTO> availableRooms;
}