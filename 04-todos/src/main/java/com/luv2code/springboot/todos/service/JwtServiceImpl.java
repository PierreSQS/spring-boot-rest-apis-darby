package com.luv2code.springboot.todos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return "";
    }

    @Override
    public boolean isTokenExpired(String token, UserDetails userDetails) {
        return false;
    }

}
