# Requirements for Adding DTOs to the User Controller

## Overview
This document outlines the requirements for refactoring the User Controller to use Data Transfer Objects (DTOs) with MapStruct for object mapping. This change will improve separation of concerns, make the API more maintainable, and follow best practices for Spring Boot applications.

## Current Implementation
Currently, the UserController uses the UserResponse class as a response object. The conversion from SecurityUser to UserResponse is done manually in the UserServiceImpl class through direct building of the UserResponse object. The UserMapper class exists but is not using MapStruct and is not being utilized in the UserServiceImpl.

## Requirements

### 1. Update UserMapper Interface
- Convert the existing UserMapper class to a MapStruct interface in the package `com.luv2code.springboot.todos.mapper`
- Define mapping methods:
  - `UserResponseDTO userToUserResponseDTO(User user)` - Maps a User entity to a UserResponseDTO
  - `UserResponseDTO securityUserToUserResponseDTO(SecurityUser securityUser)` - Maps a SecurityUser to a UserResponseDTO
  - Include a mapping for `fullName` that combines `firstName` and `lastName` from the User/SecurityUser
- The mapper should be configured as a Spring component
- When mapping from DTO to entity, the `id` property should be ignored

### 2. Update UserService Interface
- Update the return type in the UserService interface:
  - Change `UserResponse getUserInfo()` to `UserResponseDTO getUserInfo()`
- Keep the other method signatures unchanged:
  - `void deleteUser()`
  - `void updatePassword(PasswordUpdateRequest passwordUpdateRequest)`

### 3. Update UserServiceImpl
- Inject the UserMapper into the UserServiceImpl class
- Replace the manual mapping in the `getUserInfo()` method with a call to the UserMapper
- Update the implementation to use the mapper for any entity-to-DTO conversions

### 4. Update UserController
- Update the controller method to use UserResponseDTO:
  - Change `UserResponse getUserInfo()` to `UserResponseDTO getUserInfo()`
- Keep the other method signatures unchanged:
  - `void deleteUser()`
  - `void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest)`

### 5. Testing
- Update any tests that rely on the UserResponse class to use UserResponseDTO instead
- Ensure all tests pass after the refactoring

## Implementation Guidelines
1. Update the UserMapper to use MapStruct first
2. Update the service interface and implementation next
3. Update the controller last, after the service layer changes are complete
4. Run tests after each major change to ensure functionality is preserved
5. Follow the existing project conventions for package structure and naming

## Expected Outcome
After implementing these changes, the User Controller will use DTOs for data transfer, with MapStruct handling the mapping between entities and DTOs. This will improve separation of concerns, make the API more maintainable, and align with the best practices for Spring Boot applications.