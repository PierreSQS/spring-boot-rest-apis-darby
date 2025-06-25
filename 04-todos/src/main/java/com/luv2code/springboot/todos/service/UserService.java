package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.dto.UserResponseDTO;
import com.luv2code.springboot.todos.request.PasswordUpdateRequest;

public interface UserService {
    UserResponseDTO getUserInfo();

    void deleteUser();

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
