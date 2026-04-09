package com.mediconnect.user_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
