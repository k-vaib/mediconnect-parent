package com.mediconnect.doctor_service.dto.doctor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateDoctorDto {

    @NotNull
    private Long userId;

    @NotBlank(message = "specialization cannot be blank")
    private String specialization;

    @Min(value = 1, message = "fees cannot be zero.")
    private Double fee;

    private boolean active=true;


}
