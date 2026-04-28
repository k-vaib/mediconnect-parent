package com.mediconnect.doctor_service.repository;

import com.mediconnect.doctor_service.entity.Slot;
import com.mediconnect.doctor_service.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByDoctorIdAndDateAndActiveTrue(Long doctorId, LocalDate date);
    List<Slot> findByDoctorIdAndActiveTrue(Long doctorId);
    List<Slot> findByDoctorIdAndDateAndStatusAndActiveTrue(Long doctorId, LocalDate date, SlotStatus status);
    Optional<Slot> findByIdAndDoctorId(Long slotId, Long doctorId);


}
