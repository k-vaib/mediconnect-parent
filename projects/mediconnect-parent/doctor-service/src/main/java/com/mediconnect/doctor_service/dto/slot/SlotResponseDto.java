    package com.mediconnect.doctor_service.dto.slot;

    import com.mediconnect.doctor_service.entity.DoctorProfile;
    import com.mediconnect.doctor_service.entity.SlotStatus;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;

    import java.time.LocalDate;
    import java.time.LocalTime;

    @Getter
    @Setter
    @Builder
    public class SlotResponseDto {
        private Long id;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private SlotStatus status;
        private Long doctorId;
    }
