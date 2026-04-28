package com.mediconnect.doctor_service.service.vaildation;

import com.mediconnect.doctor_service.dto.slot.CreateSlotDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class SlotTimeValidator implements ConstraintValidator<ValidSlotTime, CreateSlotDto> {

    @Override
    public boolean isValid(CreateSlotDto dto, ConstraintValidatorContext context) {
        if(dto.getStartTime() == null || dto.getEndTime() == null) {
            return true;
        }
        return dto.getEndTime().isAfter(dto.getStartTime());
    }

}
