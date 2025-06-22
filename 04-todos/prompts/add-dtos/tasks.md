# Task List for Adding DTOs to the Admin Controller

## 1. Create UserResponseDTO
- [ ] 1.1. Create the `com.luv2code.springboot.todos.dto` package if it doesn't exist
- [ ] 1.2. Create the `UserResponseDTO` class with the following properties:
  - [ ] 1.2.1. `id` (Long)
  - [ ] 1.2.2. `fullName` (String)
  - [ ] 1.2.3. `email` (String)
  - [ ] 1.2.4. `authorities` (Collection<? extends GrantedAuthority>)
- [ ] 1.3. Add Lombok annotations:
  - [ ] 1.3.1. `@Data` for getters, setters, equals, hashCode, and toString
  - [ ] 1.3.2. `@Builder` for the builder pattern

## 2. Create UserMapper Interface
- [ ] 2.1. Create the `com.luv2code.springboot.todos.mapper` package if it doesn't exist
- [ ] 2.2. Create the `UserMapper` interface
- [ ] 2.3. Define the mapping method: `UserResponseDTO userToUserResponseDTO(User user)`
- [ ] 2.4. Add a mapping for `fullName` that combines `firstName` and `lastName` using MapStruct expression
- [ ] 2.5. Add the `@Mapper` annotation with the Spring component model

## 3. Update AdminService Interface
- [ ] 3.1. Change return type of `getAllUsers()` from `List<UserResponse>` to `List<UserResponseDTO>`
- [ ] 3.2. Change return type of `promoteToAdmin(long userId)` from `UserResponse` to `UserResponseDTO`
- [ ] 3.3. Update import statements to include the new DTO class

## 4. Update AdminServiceImpl
- [ ] 4.1. Inject the `UserMapper` using constructor injection
- [ ] 4.2. Update the implementation of `getAllUsers()` to use the mapper
- [ ] 4.3. Update the implementation of `promoteToAdmin(long userId)` to use the mapper
- [ ] 4.4. Remove the now-unused `buidUserResponse` method
- [ ] 4.5. Update import statements to include the new mapper and DTO classes

## 5. Update AdminController
- [ ] 5.1. Change return type of `getAllUsers()` from `Iterable<UserResponse>` to `Iterable<UserResponseDTO>`
- [ ] 5.2. Change return type of `promoteToAdmin(@PathVariable long userId)` from `UserResponse` to `UserResponseDTO`
- [ ] 5.3. Update import statements to include the new DTO class

## 6. Testing
- [ ] 6.1. Run the application to ensure it compiles and starts correctly
- [ ] 6.2. Test the API endpoints to ensure they still work as expected
- [ ] 6.3. Update any tests that rely on the `UserResponse` class to use `UserResponseDTO` instead