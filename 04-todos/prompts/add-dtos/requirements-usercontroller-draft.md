Update the UserController to use the UserResponseDTO. When converting from a DTO to the JPA 
 entity, ignore the property for id. Convert the service layer to accept DTO objects and to 
use the Mapstruct mapper for type conversions. Update the controller methods to use the new DTO pojo for input and access
to service methods. 