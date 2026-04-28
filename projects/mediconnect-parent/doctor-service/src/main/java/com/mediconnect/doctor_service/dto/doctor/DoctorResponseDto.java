package com.mediconnect.doctor_service.dto.doctor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DoctorResponseDto {
    private Long id;
    private Long userId;
    private String specialization;
    private Double fee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
