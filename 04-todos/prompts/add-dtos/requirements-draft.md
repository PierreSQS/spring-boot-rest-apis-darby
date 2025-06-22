Update the AdminController to use DTOs. In the package `response`, replace the UserResponse with a new POJO called UserResponseDTO with the same properties 
as the UserResponse. The DTO should use annotations from Project Lombok, including Builder. Create a Mapstruct mapper 
to convert to and from the DTO. Mappers should be added to the package `mappers`. When converting from a DTO to the JPA 
 entity, ignore the property for id. Convert the service layer to accept DTO objects and to 
use the Mapstruct mapper for type conversions. Update the controller methods to use the new DTO pojo for input and access
to service methods. 