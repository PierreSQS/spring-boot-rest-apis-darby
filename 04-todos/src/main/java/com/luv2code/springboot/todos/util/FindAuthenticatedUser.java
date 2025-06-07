package com.luv2code.springboot.todos.util;

import com.luv2code.springboot.todos.entity.SecurityUser;

public interface FindAuthenticatedUser {

    SecurityUser getAuthenticatedUser();
}
