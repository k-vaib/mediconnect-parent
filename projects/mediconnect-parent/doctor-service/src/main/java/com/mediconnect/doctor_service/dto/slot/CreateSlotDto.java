package com.mediconnect.doctor_service.dto.slot;

import com.mediconnect.doctor_service.entity.SlotStatus;
import com.mediconnect.doctor_service.service.vaildation.ValidSlotTime;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ValidSlotTime
public class CreateSlotDto {
    @FutureOrPresent(message = "Date must be today or in future.")
    private LocalDate date;
    @NotNull(message = "Start time is required.")
    private LocalTime startTime;
    @NotNull(message = "End time is required.")
    private LocalTime endTime;
    @NotNull(message = "Status is required.")
    private SlotStatus status;
}
