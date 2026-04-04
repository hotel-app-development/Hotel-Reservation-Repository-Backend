// src/test/java/com/spring/hotelreservationsystem/mapper/RoomMapperTest.java

package com.spring.hotelreservationsystem.mapper;

import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.models.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {

    @Test
    void toDTO() {
        Room room = new Room();
        room.setBeds(2);
        room.setPrice(100);

        RoomDTO dto = RoomMapper.toDTO(room);

        assertEquals(2, dto.getBeds());
        assertEquals(100, dto.getPrice());
    }
}