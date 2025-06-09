package com.luv2code.springboot.todos.entity;

import jakarta.persistence.Embeddable;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
public class Authority implements GrantedAuthority {

    private String role;

    public Authority() {}

    public Authority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
