package com.luv2code.springboot.todos.util;

import com.luv2code.springboot.todos.entity.SecurityUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FindAuthenticatedUserImpl implements FindAuthenticatedUser{
    @Override
    public SecurityUser getAuthenticatedUser() {
        // get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // check if the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()
                || (authentication.getPrincipal().equals("anonymousUser"))) {
            throw new AccessDeniedException("Authentication is required to delete a user");
        }

        // get the logged-in user
        return (SecurityUser) authentication.getPrincipal();
    }
}
