package com.spring.hotelreservationsystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDTO {
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}