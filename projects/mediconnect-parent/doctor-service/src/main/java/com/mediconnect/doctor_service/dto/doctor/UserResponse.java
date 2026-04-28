package com.mediconnect.doctor_service.dto.doctor;

import com.mediconnect.doctor_service.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String userName;
    private String email;
    private String phone;
    private Role role;
    private boolean active;
}
