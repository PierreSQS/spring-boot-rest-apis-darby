package com.luv2code.springboot.todos.controller;

import com.luv2code.springboot.todos.dto.UserResponseDTO;
import com.luv2code.springboot.todos.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Rest API Endpoints", description = "Operations related to managing Admin Users.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get all users", description = "Fetch all users in the system.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Iterable<UserResponseDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Operation(summary = "Promote a user to admin", description = "Promote a user to admin role.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}")
    public UserResponseDTO promoteToAdmin(@PathVariable long userId) {
        return adminService.promoteToAdmin(userId);
    }

    @Operation(summary = "Delete a non-admin user", description = "Delete a non-admin user from the system.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteNonAdminUser(@PathVariable long userId) {
        adminService.deleteNonAdminUser(userId);
    }
}
