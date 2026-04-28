package com.mediconnect.appointment_service.dto;

import com.mediconnect.appointment_service.entity.AppointmentStatus;
import com.mediconnect.appointment_service.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class AppointmentResponseDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long slotId;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
    private PaymentStatus paymentStatus;
}
