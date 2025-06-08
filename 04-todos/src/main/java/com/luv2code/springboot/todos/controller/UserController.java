package com.luv2code.springboot.todos.controller;

import com.luv2code.springboot.todos.request.PasswordUpdateRequest;
import com.luv2code.springboot.todos.response.UserResponse;
import com.luv2code.springboot.todos.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Rest API Endpoints", description = "Operations related to infos about the current user.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users") public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public UserResponse getUserInfo() {
        return userService.getUserInfo();
    }

    @DeleteMapping("/delete")
    public void deleteUser() {
        userService.deleteUser();
    }

    @PutMapping("/update-password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        userService.updatePassword(passwordUpdateRequest);
    }

}
