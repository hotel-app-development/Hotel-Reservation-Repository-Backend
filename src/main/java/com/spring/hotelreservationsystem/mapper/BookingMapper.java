package com.spring.hotelreservationsystem.mapper;

import com.spring.hotelreservationsystem.dto.BookingDTO;
import com.spring.hotelreservationsystem.dto.BookingRequestDTO;
import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.models.User;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser() != null ? booking.getUser().getId() : null);
        dto.setRoomId(booking.getRoom() != null ? booking.getRoom().getId() : null);
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setStatus(booking.getStatus() != null ? booking.getStatus().name() : null);
        return dto;
    }

    public static Booking toEntity(BookingRequestDTO dto, User user, Room room) {
        if (dto == null) return null;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        return booking;
    }
}