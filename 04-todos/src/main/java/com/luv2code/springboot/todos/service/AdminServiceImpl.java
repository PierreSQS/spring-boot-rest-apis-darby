package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.dto.UserResponseDTO;
import com.luv2code.springboot.todos.entity.Authority;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.mapper.UserMapper;
import com.luv2code.springboot.todos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        // Fetch all users from the repository and map them to UserResponseDTO objects
        return userRepo.findAll().stream()
                .map(userMapper::userToUserResponseDTO)
                .toList();

    }

    @Transactional
    @Override
    public UserResponseDTO promoteToAdmin(long userId) {
        // Find the user by ID
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id: " + userId+ " not found"));

        // If found, get the user's authorities
        List<Authority> authorities = user.getAuthorities();

        // Check if the user is already an admin
        if (authorities.contains(new Authority(ADMIN_ROLE))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id: " + userId+ " is already an admin");
        }

        // Add the admin role to the user
        authorities.add(new Authority(ADMIN_ROLE));

        // Save the updated user
        User updatedUser = userRepo.save(user);

        // Return the updated user as a UserResponseDTO
        return userMapper.userToUserResponseDTO(updatedUser);
    }

    @Transactional
    @Override
    public void deleteNonAdminUser(long userId) {
        // Check if the user exists
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id: " + userId + " not found"));

        // Check if the user is an admin
        if (user.getAuthorities().contains(new Authority(ADMIN_ROLE))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Deleting an admin user is not allowed");
        }

        // If the user is not an admin, delete the user
        userRepo.delete(user);
    }

}
