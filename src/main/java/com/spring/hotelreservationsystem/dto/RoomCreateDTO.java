package com.spring.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class RoomCreateDTO {
    private String roomType;
    private int beds;
    private double price;
}