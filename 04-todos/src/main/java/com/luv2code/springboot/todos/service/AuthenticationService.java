package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.request.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest registerRequest) throws Exception;
}
