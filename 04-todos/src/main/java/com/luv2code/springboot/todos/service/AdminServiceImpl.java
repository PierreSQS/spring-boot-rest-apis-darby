package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.UserRepository;
import com.luv2code.springboot.todos.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepo;

    @Override
    public List<UserResponse> getAllUsers() {
        // Fetch all users from the repository and map them to UserResponse objects
        return userRepo.findAll().stream()
                .map(this::buidUserResponse)
                .toList();

    }

    private UserResponse buidUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .build();
    }
}
