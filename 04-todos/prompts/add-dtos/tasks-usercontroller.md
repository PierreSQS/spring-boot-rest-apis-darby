# Task List for Adding DTOs to the User Controller

This document contains a detailed task list for implementing the changes outlined in the plan-usercontroller.md file. Each task has a placeholder [ ] that can be marked as [x] upon completion.

## 1. Update UserMapper Interface

### 1.1 Convert to MapStruct Interface
- [x] Remove existing implementation in UserMapper
- [x] Add MapStruct @Mapper annotation
- [x] Configure as Spring component with componentModel = "spring"

### 1.2 Define Required Mapping Methods
- [x] Add method: UserResponseDTO userToUserResponseDTO(User user)
- [x] Add method: UserResponseDTO securityUserToUserResponseDTO(SecurityUser securityUser)

### 1.3 Configure Special Mappings
- [x] Add mapping for fullName that combines firstName and lastName
- [x] Configure id property to be ignored when mapping from DTO to entity

## 2. Update UserService Interface

### 2.1 Update Method Return Types
- [x] Change getUserInfo() return type from UserResponse to UserResponseDTO
- [x] Update import statements

### 2.2 Verify Other Methods
- [x] Ensure deleteUser() method signature remains unchanged
- [x] Ensure updatePassword(PasswordUpdateRequest) method signature remains unchanged

## 3. Update UserServiceImpl

### 3.1 Update Dependencies
- [x] Inject UserMapper into UserServiceImpl

### 3.2 Update Implementation
- [x] Replace manual UserResponse building with UserMapper call in getUserInfo()
- [x] Update return type to UserResponseDTO
- [x] Update import statements

## 4. Update UserController

### 4.1 Update Method Return Types
- [x] Change getUserInfo() return type from UserResponse to UserResponseDTO
- [x] Update import statements

### 4.2 Verify Other Methods
- [x] Ensure deleteUser() method signature remains unchanged
- [x] Ensure updatePassword() method signature remains unchanged

## 5. Update Tests

### 5.1 Update UserControllerTest
- [x] Identify and update references to UserResponse
- [x] Replace with UserResponseDTO
- [x] Update import statements
- [x] Verify test cases still pass

### 5.2 Update UserControllerIT
- [x] Identify and update references to UserResponse
- [x] Replace with UserResponseDTO
- [x] Update import statements
- [x] Verify test cases still pass

### 5.3 Update UserControllerWithTokenIT
- [x] Identify and update references to UserResponse
- [x] Replace with UserResponseDTO
- [x] Update import statements
- [x] Verify test cases still pass

## 6. Verification

### 6.1 Run Tests
- [x] Run all tests to ensure functionality is preserved
- [x] Verify that API responses remain consistent after refactoring

### 6.2 Manual Testing
- [x] Test the API endpoints manually to verify correct behavior
- [x] Verify that the response format matches the expected structure

## 7. Documentation

### 7.1 Update Documentation
- [x] Update any API documentation to reflect the changes
- [x] Document the use of DTOs and MapStruct in the codebase
