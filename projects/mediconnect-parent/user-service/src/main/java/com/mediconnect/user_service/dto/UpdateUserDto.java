package com.mediconnect.user_service.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UpdateUserDto {
    @Size(min=5, max=50, message="Name must be between 5 and 50")
    private String userName;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String phone;

}
