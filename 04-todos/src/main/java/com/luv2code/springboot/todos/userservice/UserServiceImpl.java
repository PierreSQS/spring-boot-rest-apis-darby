package com.luv2code.springboot.todos.userservice;

import com.luv2code.springboot.todos.entity.Authority;
import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.response.UserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public UserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        return UserResponse.builder()
                .id(securityUser.getId())
                .fullName(securityUser.getFirstName()+" "+securityUser.getLastName())
                .email(securityUser.getEmail())
                .authorities((List<Authority>) securityUser.getAuthorities())
                .build();
    }
}
