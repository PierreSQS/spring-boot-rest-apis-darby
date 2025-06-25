package com.luv2code.springboot.todos.controller;

import com.luv2code.springboot.todos.dto.UserResponseDTO;
import com.luv2code.springboot.todos.request.PasswordUpdateRequest;
import com.luv2code.springboot.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Rest API Endpoints", description = "Operations related to the current user.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user info", description = "Retrieve information about the currently authenticated user.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponseDTO getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(summary = "Delete user", description = "Delete the currently authenticated user from the system.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void deleteUser() {
        userService.deleteUser();
    }

    @Operation(summary = "Update user password", description = "Update the password for the currently authenticated user.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update-password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        userService.updatePassword(passwordUpdateRequest);
    }

}
