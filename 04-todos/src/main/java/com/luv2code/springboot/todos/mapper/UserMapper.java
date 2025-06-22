package com.luv2code.springboot.todos.mapper;

import com.luv2code.springboot.todos.dto.UserResponseDTO;
import com.luv2code.springboot.todos.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO userToUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .build();
    }
}
