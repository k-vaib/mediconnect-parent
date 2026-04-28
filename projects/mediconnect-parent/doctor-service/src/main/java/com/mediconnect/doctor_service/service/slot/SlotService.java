package com.mediconnect.doctor_service.service.slot;

import com.mediconnect.doctor_service.dto.slot.CancelSlotDto;
import com.mediconnect.doctor_service.dto.slot.CreateSlotDto;
import com.mediconnect.doctor_service.dto.slot.SlotResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface SlotService {
    SlotResponseDto createSlot(Long doctorId, CreateSlotDto dto);

    List<SlotResponseDto> getAllSlots(Long doctorId);

    SlotResponseDto getSlotById(Long slotId);

    List<SlotResponseDto> getAvailableSlots(Long doctorId, LocalDate date);

    void deleteSlot(Long doctorId, Long slotId);

    SlotResponseDto bookSlot(Long slotId);

    SlotResponseDto cancelSlot(Long slotId, CancelSlotDto dto);
}
