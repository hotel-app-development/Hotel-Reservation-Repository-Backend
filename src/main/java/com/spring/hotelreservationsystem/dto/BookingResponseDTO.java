package com.spring.hotelreservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private String message;
}