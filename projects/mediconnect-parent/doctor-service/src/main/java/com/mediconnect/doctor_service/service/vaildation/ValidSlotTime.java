package com.mediconnect.doctor_service.service.vaildation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SlotTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSlotTime {
    String message() default "End time must be greater than start time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
