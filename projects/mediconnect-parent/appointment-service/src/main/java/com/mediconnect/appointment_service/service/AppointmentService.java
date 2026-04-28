package com.mediconnect.appointment_service.service;

import com.mediconnect.appointment_service.dto.AppointmentResponseDto;
import com.mediconnect.appointment_service.dto.CreateAppointmentDto;
import jakarta.validation.Valid;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(CreateAppointmentDto dto);
}
