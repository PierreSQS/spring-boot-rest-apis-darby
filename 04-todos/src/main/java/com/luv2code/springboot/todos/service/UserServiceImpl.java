package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.UserRepository;
import com.luv2code.springboot.todos.request.PasswordUpdateRequest;
import com.luv2code.springboot.todos.response.UserResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final FindAuthenticatedUser findAuthenticatedUser;

    private final PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        SecurityUser securityUser = findAuthenticatedUser.getAuthenticatedUser();

        User user = getDbUser(securityUser);

        // Validate that the update password matches the old password
        if (!isUpdatePasswordMatch(user.getPassword(), passwordUpdateRequest.getPwdToUpdate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password to update is incorrect");
        }

        // Confirm that the new password matches the confirmation
        if (!isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(), passwordUpdateRequest.getConfirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password confirmation does not match");
        }

        // Validate that the new password is different from the old one
        if (!isNewPasswordDifferent(passwordUpdateRequest.getPwdToUpdate(), passwordUpdateRequest.getNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from the old one");
        }

        // Update the user's password
        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepo.save(user);
    }

    private boolean isUpdatePasswordMatch(String pwdToUpdate, String oldPassword) {
        return passwordEncoder.matches(oldPassword, pwdToUpdate);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String newPasswordConfirmation) {
        return newPassword.equals(newPasswordConfirmation);
    }

    private boolean isNewPasswordDifferent(String oldPassword, String newPassword) {
        return !oldPassword.equals(newPassword);
    }

    private User getDbUser(SecurityUser securityUser) {
        // get the real user from the DB since the SecurityUser is not a JPA entity
        return userRepo.findById(securityUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    private boolean isLastAdmin(SecurityUser securityUser) {
        // get the real user from the DB since the SecurityUser is not a JPA entity
        User userFromDB = getDbUser(securityUser);

        // is the user an admin?
        if (userFromDB.getAuthorities().stream()
                .noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return false; // User is not an admin, go ahead with the deletion process
        }

        // Check if this is the last admin user
        return userRepo.countAdmins() <= 1;
    }

}
