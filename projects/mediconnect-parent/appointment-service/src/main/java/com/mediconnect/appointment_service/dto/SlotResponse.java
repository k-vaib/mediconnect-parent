package com.mediconnect.appointment_service.dto;

import com.mediconnect.appointment_service.entity.SlotStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SlotResponse {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotStatus status;
    private Long doctorId;
    private boolean active;
}
