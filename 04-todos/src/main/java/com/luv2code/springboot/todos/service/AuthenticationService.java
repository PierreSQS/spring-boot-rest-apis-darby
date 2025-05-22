package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.request.AuthenticationRequest;
import com.luv2code.springboot.todos.request.RegisterRequest;
import com.luv2code.springboot.todos.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest registerRequest) throws Exception;

    // Generate JWT token and return in Response Object
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
