package com.luv2code.springboot.todos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordUpdateRequest {

    @NotEmpty(message = "Old password to update is mandatory")
    @Size(min = 5, max = 30, message = "Old password to update must be at least 5 characters long")
    private String pwdToUpdate;

    @NotEmpty(message = "New password is mandatory")
    @Size(min = 5, max = 30, message = "New password must be at least 5 characters long")
    private String newPassword;

    @NotEmpty(message = "Confirmed password is mandatory")
    @Size(min = 5, max = 30, message = "Confirmed password must be at least 5 characters long")
    private String confirmedPassword;

}
