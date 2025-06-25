# Implementation Plan for Adding DTOs to the User Controller

## Overview
This document outlines the implementation plan for refactoring the User Controller to use Data Transfer Objects (DTOs) with MapStruct for object mapping. The plan follows the requirements specified in the requirements-usercontroller.md file.

## Current State Analysis
- The UserController currently returns UserResponse objects
- UserServiceImpl manually builds UserResponse objects
- UserMapper exists but is not using MapStruct and is not utilized in UserServiceImpl
- UserResponseDTO class exists with the same structure as UserResponse but in a different package

## Implementation Plan

### 1. Update UserMapper Interface
1. Convert the existing UserMapper class to a MapStruct interface:
   - Remove the existing implementation
   - Add MapStruct annotations (@Mapper)
   - Configure it as a Spring component (componentModel = "spring")

2. Define the required mapping methods:
   - `UserResponseDTO userToUserResponseDTO(User user)`
   - `UserResponseDTO securityUserToUserResponseDTO(SecurityUser securityUser)`

3. Add mapping configuration for fullName:
   - Create a mapping expression that combines firstName and lastName

4. Ensure the id property is ignored when mapping from DTO to entity

### 2. Update UserService Interface
1. Update the return type in the getUserInfo method:
   - Change `UserResponse getUserInfo()` to `UserResponseDTO getUserInfo()`
   
2. Keep other method signatures unchanged:
   - `void deleteUser()`
   - `void updatePassword(PasswordUpdateRequest passwordUpdateRequest)`

### 3. Update UserServiceImpl
1. Inject the UserMapper into the UserServiceImpl class
2. Update the getUserInfo method:
   - Replace the manual UserResponse building with a call to the UserMapper
   - Return UserResponseDTO instead of UserResponse

### 4. Update UserController
1. Update the controller method:
   - Change `UserResponse getUserInfo()` to `UserResponseDTO getUserInfo()`
   - Update the import statements

2. Keep other method signatures unchanged:
   - `void deleteUser()`
   - `void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest)`

### 5. Update Tests
1. Identify tests that use UserResponse:
   - UserControllerTest
   - UserControllerIT
   - UserControllerWithTokenIT

2. Update these tests to use UserResponseDTO instead of UserResponse

## Implementation Order
1. Update the UserMapper to use MapStruct
2. Update the UserService interface
3. Update the UserServiceImpl
4. Update the UserController
5. Update the tests

## Dependencies
- MapStruct must be properly configured in the project's pom.xml

## Testing Strategy
- Run tests after each major change to ensure functionality is preserved
- Verify that the API responses remain consistent after the refactoring

## Expected Outcome
After implementing these changes, the User Controller will use DTOs for data transfer, with MapStruct handling the mapping between entities and DTOs. This will improve separation of concerns, make the API more maintainable, and align with the best practices for Spring Boot applications.