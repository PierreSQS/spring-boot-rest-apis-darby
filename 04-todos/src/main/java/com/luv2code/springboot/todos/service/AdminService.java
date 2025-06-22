package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.dto.UserResponseDTO;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO promoteToAdmin(long userId);
    void deleteNonAdminUser(long userId);
}
