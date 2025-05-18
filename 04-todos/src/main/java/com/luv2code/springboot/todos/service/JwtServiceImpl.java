package com.luv2code.springboot.todos.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

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
        return Jwts.builder()
                .claims(claims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public boolean isTokenExpired(String token, UserDetails userDetails) {
        return false;
    }

    // Create a secret key using the signature algorithm
    private SecretKey getSigningKey() {
        return Jwts.SIG.HS256.key().build();
    }

}
