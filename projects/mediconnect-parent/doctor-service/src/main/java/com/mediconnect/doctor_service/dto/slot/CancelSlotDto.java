package com.mediconnect.doctor_service.dto.slot;

import com.mediconnect.doctor_service.entity.CancellationReason;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelSlotDto {
    @NotNull(message = "Cancellation reason is required")
    private CancellationReason reason;

    private Long userId; // who cancelled (optional for now)
}
