# Requirements for Adding DTOs to the Admin Controller

## Overview
This document outlines the requirements for refactoring the Admin Controller to use Data Transfer Objects (DTOs) with MapStruct for object mapping. This change will improve separation of concerns, make the API more maintainable, and follow best practices for Spring Boot applications.

## Current Implementation
Currently, the AdminController directly uses the UserResponse class as a response object. The conversion from User entity to UserResponse is done manually in the AdminServiceImpl class through the `buidUserResponse` method.

## Requirements

### 1. Create UserResponseDTO
- Create a new class called `UserResponseDTO` in the package `com.luv2code.springboot.todos.dto`
- The DTO should have the same properties as the current UserResponse:
  - `id` (Long)
  - `fullName` (String)
  - `email` (String)
  - `authorities` (Collection<? extends GrantedAuthority>)
- Use Lombok annotations:
  - `@Data` for getters, setters, equals, hashCode, and toString
  - `@Builder` for the builder pattern

### 2. Create UserMapper Interface
- Create a new interface called `UserMapper` in the package `com.luv2code.springboot.todos.mapper`
- Use MapStruct to define mapping methods:
  - `UserResponseDTO userToUserResponseDTO(User user)` - Maps a User entity to a UserResponseDTO
  - Include a mapping for `fullName` that combines `firstName` and `lastName` from the User entity
- The mapper should be configured as a Spring component

### 3. Update AdminService Interface
- Update the return types in the AdminService interface:
  - Change `List<UserResponse> getAllUsers()` to `List<UserResponseDTO> getAllUsers()`
  - Change `UserResponse promoteToAdmin(long userId)` to `UserResponseDTO promoteToAdmin(long userId)`

### 4. Update AdminServiceImpl
- Inject the UserMapper into the AdminServiceImpl class
- Replace the manual mapping in the `buidUserResponse` method with calls to the UserMapper
- Update the implementation of the service methods to return UserResponseDTO objects

### 5. Update AdminController
- Update the controller methods to use UserResponseDTO:
  - Change `Iterable<UserResponse> getAllUsers()` to `Iterable<UserResponseDTO> getAllUsers()`
  - Change `UserResponse promoteToAdmin(@PathVariable long userId)` to `UserResponseDTO promoteToAdmin(@PathVariable long userId)`

### 6. Testing
- Update any tests that rely on the UserResponse class to use UserResponseDTO instead
- Ensure all tests pass after the refactoring

## Implementation Guidelines
1. Create the DTO and mapper first, then update the service interface and implementation
2. Update the controller last, after the service layer changes are complete
3. Run tests after each major change to ensure functionality is preserved
4. Follow the existing project conventions for package structure and naming

## Expected Outcome
After implementing these changes, the Admin Controller will use DTOs for data transfer, with MapStruct handling the mapping between entities and DTOs. This will improve separation of concerns and make the API more maintainable.