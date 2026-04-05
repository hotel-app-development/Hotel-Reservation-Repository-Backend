package com.spring.hotelreservationsystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Room")
@Table(
    name = "rooms",
    indexes = {
        @Index(name = "idx_rooms_status", columnList = "status")
    }
)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "beds" , nullable = false)
    private int beds;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Booking> bookings;

    //Constructor without id (for tests or creation)
    public Room(String roomType, int beds, double price, String status) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.status = status;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

}
