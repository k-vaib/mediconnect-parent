package com.mediconnect.appointment_service.service;

import com.mediconnect.appointment_service.client.DoctorClient;
import com.mediconnect.appointment_service.client.UserClient;
import com.mediconnect.appointment_service.dto.AppointmentResponseDto;
import com.mediconnect.appointment_service.dto.CreateAppointmentDto;
import com.mediconnect.appointment_service.dto.SlotResponse;
import com.mediconnect.appointment_service.dto.UserResponse;
import com.mediconnect.appointment_service.entity.*;
import com.mediconnect.appointment_service.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final UserClient userClient;
    private final DoctorClient doctorClient;

    @Override
    public AppointmentResponseDto createAppointment(CreateAppointmentDto dto) {
        UserResponse user =  userClient.getUserById(dto.getPatientId());
        if(!user.isActive()) {
            throw  new IllegalStateException("User is not active");
        }

        if(!user.getRole().equals(Role.PATIENT)){
            throw  new IllegalStateException("User is not patient");
        }

        SlotResponse slot = doctorClient.getSlotById(dto.getSlotId());

        if (!slot.isActive()) {
            throw new IllegalStateException("Slot is inactive");
        }

        if(!slot.getStatus().equals(SlotStatus.AVAILABLE)){
            throw  new IllegalStateException("Slot is not available");
        }

        if(!slot.getDoctorId().equals(dto.getDoctorId())) {
            throw new IllegalStateException("slot does not belong to doctor");
        }

        if(appointmentRepository.existsBySlotId(dto.getSlotId())){
            throw  new IllegalStateException("Slot already exists");
        }


        Appointment appointment = Appointment.builder()
                .patientId(dto.getPatientId())
                .doctorId(slot.getDoctorId())
                .slotId(slot.getId())
                .appointmentDate(slot.getDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(AppointmentStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapToDto(savedAppointment);
    }

    //helper method
    private AppointmentResponseDto mapToDto(Appointment appointment) {
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .slotId(appointment.getSlotId())
                .date(appointment.getAppointmentDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .paymentStatus(appointment.getPaymentStatus())
                .build();

    }
}
