package com.luv2code.springboot.todos.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class UserResponse {
    private Long id;

    private String fullName;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;
}
