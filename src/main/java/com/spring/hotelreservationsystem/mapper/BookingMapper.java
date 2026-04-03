package com.spring.hotelreservationsystem.mapper;

import com.spring.hotelreservationsystem.dto.BookingDTO;
import com.spring.hotelreservationsystem.models.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        if (booking == null)
            return null;
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser() != null ? booking.getUser().getId() : null);
        dto.setRoomId(booking.getRoom() != null ? booking.getRoom().getId() : null);
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setStatus(booking.getStatus() != null ? booking.getStatus().name() : null);
        return dto;
    }
}