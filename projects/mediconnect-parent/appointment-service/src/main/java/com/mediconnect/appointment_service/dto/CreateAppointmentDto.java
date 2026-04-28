package com.mediconnect.appointment_service.dto;

import com.mediconnect.appointment_service.entity.AppointmentStatus;
import com.mediconnect.appointment_service.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter

public class CreateAppointmentDto {
    @NotNull(message = "patientId is required.")
    private Long patientId;
    @NotNull(message = "doctorId is required.")
    private Long doctorId;
    @NotNull(message = "slotId is required.")
    private Long slotId;


}
