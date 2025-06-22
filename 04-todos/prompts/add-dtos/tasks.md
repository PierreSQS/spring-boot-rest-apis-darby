# Task List for Adding DTOs to the Admin Controller

## 1. Create UserResponseDTO
- [x] 1.1. Create the `com.luv2code.springboot.todos.dto` package if it doesn't exist
- [x] 1.2. Create the `UserResponseDTO` class with the following properties:
  - [x] 1.2.1. `id` (Long)
  - [x] 1.2.2. `fullName` (String)
  - [x] 1.2.3. `email` (String)
  - [x] 1.2.4. `authorities` (Collection<? extends GrantedAuthority>)
- [x] 1.3. Add Lombok annotations:
  - [x] 1.3.1. `@Data` for getters, setters, equals, hashCode, and toString
  - [x] 1.3.2. `@Builder` for the builder pattern

## 2. Create UserMapper Interface
- [x] 2.1. Create the `com.luv2code.springboot.todos.mapper` package if it doesn't exist
- [x] 2.2. Create the `UserMapper` interface
- [x] 2.3. Define the mapping method: `UserResponseDTO userToUserResponseDTO(User user)`
- [x] 2.4. Add a mapping for `fullName` that combines `firstName` and `lastName` using MapStruct expression
- [x] 2.5. Add the `@Mapper` annotation with the Spring component model

## 3. Update AdminService Interface
- [x] 3.1. Change return type of `getAllUsers()` from `List<UserResponse>` to `List<UserResponseDTO>`
- [x] 3.2. Change return type of `promoteToAdmin(long userId)` from `UserResponse` to `UserResponseDTO`
- [x] 3.3. Update import statements to include the new DTO class

## 4. Update AdminServiceImpl
- [x] 4.1. Inject the `UserMapper` using constructor injection
- [x] 4.2. Update the implementation of `getAllUsers()` to use the mapper
- [x] 4.3. Update the implementation of `promoteToAdmin(long userId)` to use the mapper
- [x] 4.4. Remove the now-unused `buidUserResponse` method
- [x] 4.5. Update import statements to include the new mapper and DTO classes

## 5. Update AdminController
- [x] 5.1. Change return type of `getAllUsers()` from `Iterable<UserResponse>` to `Iterable<UserResponseDTO>`
- [x] 5.2. Change return type of `promoteToAdmin(@PathVariable long userId)` from `UserResponse` to `UserResponseDTO`
- [x] 5.3. Update import statements to include the new DTO class

## 6. Testing
- [x] 6.1. Run the application to ensure it compiles and starts correctly
- [x] 6.2. Test the API endpoints to ensure they still work as expected
- [x] 6.3. Update any tests that rely on the `UserResponse` class to use `UserResponseDTO` instead
