package com.mediconnect.doctor_service.dto.doctor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateDoctorDto {
    @NotBlank(message = "specialization cannot be blank.")
    private String specialization;
    @Min(value = 1, message = "fee cannot be less than or equal to 0.")
    private Double fee;
}
