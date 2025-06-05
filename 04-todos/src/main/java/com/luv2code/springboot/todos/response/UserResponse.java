package com.luv2code.springboot.todos.response;

import com.luv2code.springboot.todos.entity.Authority;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;

    private String fullName;

    private String email;

    private List<Authority> authorities;
}
