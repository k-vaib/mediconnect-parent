package com.mediconnect.appointment_service.repository;

import com.mediconnect.appointment_service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsBySlotId(Long slotId);

}
