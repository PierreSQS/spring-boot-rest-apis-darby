package com.luv2code.springboot.todos.mapper;

import com.luv2code.springboot.todos.dto.UserResponseDTO;
import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "id", ignore = false)
    UserResponseDTO userToUserResponseDTO(User user);

    @Mapping(target = "fullName", expression = "java(securityUser.getFirstName() + \" \" + securityUser.getLastName())")
    @Mapping(target = "id", ignore = false)
    UserResponseDTO securityUserToUserResponseDTO(SecurityUser securityUser);
}
