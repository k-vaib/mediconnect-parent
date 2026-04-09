package com.mediconnect.user_service.dto;

import lombok.Builder;

@Builder
public class LoginResponseDto {
        private Long userId;
        private String username;
        private String email;
        private String role;
        private String message;

}
