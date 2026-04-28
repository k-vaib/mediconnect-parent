package com.mediconnect.appointment_service.controller;

import com.mediconnect.appointment_service.dto.AppointmentResponseDto;
import com.mediconnect.appointment_service.dto.CreateAppointmentDto;
import com.mediconnect.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid @RequestBody CreateAppointmentDto dto){
        AppointmentResponseDto appointment = appointmentService.createAppointment(dto);
        return ResponseEntity.ok(appointment);
    }
}
