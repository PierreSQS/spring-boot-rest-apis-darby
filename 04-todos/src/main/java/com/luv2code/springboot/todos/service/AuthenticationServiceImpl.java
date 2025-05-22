package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.Authority;
import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.UserRepository;
import com.luv2code.springboot.todos.request.AuthenticationRequest;
import com.luv2code.springboot.todos.request.RegisterRequest;
import com.luv2code.springboot.todos.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Transactional
    @Override
    public void register(RegisterRequest registerRequest) throws Exception {

        String email = registerRequest.getEmail();

        if (isEmailAlreadyInUse(email)) {
            throw new Exception("Email "+ email +" already in use");
        }
        
        User user = buildNewUser(registerRequest);
        userRepo.save(user);

    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        // Find user by email
        User user = userRepo.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with email: "+authenticationRequest.getEmail()+" not found"));

        // Generate JWT token
        String jwtToken = jwtService.generateToken(new HashMap<>(), new SecurityUser(user));

        return new AuthenticationResponse(jwtToken);
    }

    private boolean isEmailAlreadyInUse(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    private User buildNewUser(RegisterRequest registerRequest) {

        return User.builder()
                .id(0) // to force saving instead of updating
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .authorities(initializeAuthorities())
                .build();
    }

    private List<Authority> initializeAuthorities() {
        boolean istFirstUser = userRepo.count() == 0;
        if (istFirstUser) {
            return List.of(new Authority("ROLE_ADMIN"), new Authority("ROLE_USER"));
        } else {
            return List.of(new Authority("ROLE_USER"));
        }
    }
}
