package com.mediconnect.user_service.dto;

import com.mediconnect.user_service.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class CreateUserDto {

    @NotBlank(message = "name cannot be blank.")
    @Size(min = 5, max = 20, message = "name must be between 5 and 20")
    private String userName;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String phone;

    @NotNull(message = "role is required")
    private Role role;

    private boolean active = true;

}
