package com.luv2code.springboot.todos.controller;

import com.luv2code.springboot.todos.response.UserResponse;
import com.luv2code.springboot.todos.userservice.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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


}
