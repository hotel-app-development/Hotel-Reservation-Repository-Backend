package com.spring.hotelreservationsystem.mapper;

import com.spring.hotelreservationsystem.dto.UserDTO;
import com.spring.hotelreservationsystem.dto.UserRegistrationDTO;
import com.spring.hotelreservationsystem.models.Role;
import com.spring.hotelreservationsystem.models.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        return dto;
    }

    public static User toEntity(UserRegistrationDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.valueOf(dto.getRole()));

        return user;
    }
}