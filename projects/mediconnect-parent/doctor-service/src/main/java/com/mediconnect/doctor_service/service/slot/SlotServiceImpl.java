package com.mediconnect.doctor_service.service.slot;

import com.mediconnect.doctor_service.dto.slot.CancelSlotDto;
import com.mediconnect.doctor_service.dto.slot.CreateSlotDto;
import com.mediconnect.doctor_service.dto.slot.SlotResponseDto;
import com.mediconnect.doctor_service.entity.DoctorProfile;
import com.mediconnect.doctor_service.entity.Slot;
import com.mediconnect.doctor_service.entity.SlotStatus;
import com.mediconnect.doctor_service.exceptions.ResourceNotFoundException;
import com.mediconnect.doctor_service.repository.DoctorRepository;
import com.mediconnect.doctor_service.repository.SlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {
    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public SlotResponseDto createSlot(Long doctorId, CreateSlotDto dto) {
        DoctorProfile doctor = getActiveDoctor(doctorId);

        validateSlotTime(dto);
        validateNoOverlap(doctorId, dto);

        Slot slot = Slot.builder()
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .doctor(doctor)
                .status(SlotStatus.AVAILABLE)
                .active(true)
                .build();

        Slot saved = slotRepository.save(slot);

        return mapToSlotResponseDto(saved);
    }

    @Override
    public List<SlotResponseDto> getAllSlots(Long doctorId) {
        DoctorProfile doctor = getActiveDoctor(doctorId);

        List<Slot> slots = slotRepository.findByDoctorIdAndActiveTrue(doctorId);
        return slots.stream().map(this::mapToSlotResponseDto).collect(Collectors.toList());
    }

    @Override
    public SlotResponseDto getSlotById(Long slotId) {
        Slot slot = slotRepository.findById(slotId).orElseThrow(() -> new ResourceNotFoundException("Slot with id: " + slotId));
        return mapToSlotResponseDto(slot);
    }

    @Override
    public List<SlotResponseDto> getAvailableSlots(Long doctorId, LocalDate date) {
        DoctorProfile doctor = getActiveDoctor(doctorId);

        List<Slot> slots = slotRepository.findByDoctorIdAndDateAndStatusAndActiveTrue(doctorId, date, SlotStatus.AVAILABLE);
        if(date.equals(LocalDate.now())) {
            LocalTime now =  LocalTime.now();

            slots = slots.stream()
                    .filter(slot -> slot.getStartTime().isAfter(now))
                    .toList();
        }
        return slots.stream().map(this::mapToSlotResponseDto).collect(Collectors.toList());
    }

    @Override
    public void deleteSlot(Long doctorId, Long slotId) {
        DoctorProfile doctor = getActiveDoctor(doctorId);

        Slot slot = slotRepository.findByIdAndDoctorId(slotId, doctorId).orElseThrow(()-> new ResourceNotFoundException("Slot not found"));

        if(slot.getStatus() == SlotStatus.BOOKED) {
            throw new IllegalStateException("Cannot delete a booked slot.");
        }
        if (!slot.isActive()) {
            throw new IllegalStateException("Slot already deleted");
        }
        LocalDateTime slotDateTime = LocalDateTime.of(slot.getDate(), slot.getStartTime());
        if(slotDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot delete past or ongoing slots.");
        }
        slot.setActive(false);
        slotRepository.save(slot);
    }

    @Override
    public SlotResponseDto bookSlot(Long slotId) {
        Slot slot = slotRepository.findById(slotId).orElseThrow(()-> new ResourceNotFoundException("Slot not found"));

        if(!slot.isActive()) {
            throw new IllegalStateException("Slot is inactive.");
        }
        if(slot.getStatus() == SlotStatus.BOOKED) {
            throw new IllegalStateException("Slot already booked.");
        }

        LocalDateTime slotDateTime = LocalDateTime.of(slot.getDate(), slot.getStartTime());
        if(slotDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book a past slot.");
        }

        slot.setStatus(SlotStatus.BOOKED);
        slotRepository.save(slot);
        return mapToSlotResponseDto(slot);

    }

    @Override
    public SlotResponseDto cancelSlot(Long slotId, CancelSlotDto dto) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        if (!slot.isActive()) {
            throw new IllegalStateException("Slot is inactive");
        }
        System.out.println("Before check: " + slot.getStatus());
        if (slot.getStatus() == SlotStatus.AVAILABLE) {
            throw new IllegalStateException("Slot is already available");
        }

        LocalDateTime slotDateTime = LocalDateTime.of(slot.getDate(), slot.getStartTime());

        if (slotDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel past or ongoing slot");
        }


        System.out.println("Updating slot");
        slot.setStatus(SlotStatus.AVAILABLE);

        slot.setCancellationReason(dto.getReason());
        slot.setCancelledAt(LocalDateTime.now());
        slot.setCancelledByUserId(dto.getUserId());

        slotRepository.save(slot);
        return mapToSlotResponseDto(slot);
    }


    //valiations
    private void validateSlotTime(CreateSlotDto dto) {
        LocalDateTime slotStart = LocalDateTime.of(dto.getDate(), dto.getStartTime());

        if(slotStart.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Slot time cannot be in past.");
        }
    }

    private void validateNoOverlap(Long doctorId, CreateSlotDto dto) {
        List<Slot> existingSlots = slotRepository.findByDoctorIdAndDateAndActiveTrue(doctorId, dto.getDate());

        for(Slot slot : existingSlots) {
            boolean overlap = dto.getStartTime().isBefore(slot.getEndTime()) &&
                    dto.getEndTime().isAfter(slot.getStartTime());

            if(overlap) {
                throw new IllegalArgumentException("Slot overlaps with existing slot.");
            }
        }
    }

    //helper
    private DoctorProfile getActiveDoctor(Long doctorId) {
        DoctorProfile doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        if (!doctor.isActive()) {
            throw new IllegalStateException("Doctor is inactive");
        }

        return doctor;
    }

    //mapping
    private SlotResponseDto mapToSlotResponseDto(Slot slot) {
        return SlotResponseDto.builder()
                .id(slot.getId())
                .date(slot.getDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(slot.getStatus())
                .doctorId(slot.getDoctor().getId())
                .build();
    }
}
