package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.UserRepository;
import com.luv2code.springboot.todos.response.UserResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final FindAuthenticatedUser findAuthenticatedUser;


    @Override
    public UserResponse getUserInfo() {

        SecurityUser securityUser = findAuthenticatedUser.getAuthenticatedUser();

        return UserResponse.builder()
                .id(securityUser.getId())
                .fullName(securityUser.getFirstName()+" "+securityUser.getLastName())
                .email(securityUser.getEmail())
                .authorities(securityUser.getAuthorities())
                .build();
    }

    @Override
    public void deleteUser() {

        SecurityUser authenticatedUser = findAuthenticatedUser.getAuthenticatedUser();

        // is the logged-in user the last admin?
        // if so, stop the deletion process by throwing an exception
        if(isLastAdmin(authenticatedUser)) {
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete the last admin user");
        }

        userRepo.deleteById(authenticatedUser.getId());
    }

    private boolean isLastAdmin(SecurityUser securityUser) {
        // get the real user from the DB since the SecurityUser is not a JPA entity
        User foundUser = userRepo.findById(securityUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // is the user an admin?
        if (foundUser.getAuthorities().stream()
                .noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return false; // User is not an admin, go ahead with the deletion process
        }

        // Check if this is the last admin user
        return userRepo.countAdmins() <= 1;
    }
}
