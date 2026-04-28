package com.mediconnect.doctor_service.controller;

import com.mediconnect.doctor_service.dto.doctor.CreateDoctorDto;
import com.mediconnect.doctor_service.dto.doctor.DoctorResponseDto;
import com.mediconnect.doctor_service.dto.doctor.UpdateDoctorDto;
import com.mediconnect.doctor_service.dto.slot.CancelSlotDto;
import com.mediconnect.doctor_service.dto.slot.CreateSlotDto;
import com.mediconnect.doctor_service.dto.slot.SlotResponseDto;
import com.mediconnect.doctor_service.service.doctor.DoctorService;
import com.mediconnect.doctor_service.service.slot.SlotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/doctors")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final SlotService slotService;

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody @Valid CreateDoctorDto dto){
        DoctorResponseDto created = doctorService.createDoctor(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable("doctorId") Long doctorId){
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable Long doctorId, @RequestBody @Valid UpdateDoctorDto dto){
        return ResponseEntity.ok(doctorService.updateDoctor(doctorId, dto));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable Long doctorId){
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }


    //slots
    @PostMapping("/{doctorId}/slots")
    public ResponseEntity<SlotResponseDto> addSlot(@PathVariable Long doctorId, @RequestBody @Valid CreateSlotDto dto){
        SlotResponseDto created = slotService.createSlot(doctorId, dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{doctorId}/slots/all")
    public ResponseEntity<List<SlotResponseDto>> getAllSlots(@PathVariable Long doctorId){
        return ResponseEntity.ok(slotService.getAllSlots(doctorId));
    }

    @GetMapping("/slots/{slotId}")
    public ResponseEntity<SlotResponseDto> getSlotById(@PathVariable Long slotId){
        return ResponseEntity.ok(slotService.getSlotById(slotId));
    }

    @GetMapping("/{doctorId}/slots")
    public ResponseEntity<List<SlotResponseDto>> getAvailableSlots(@PathVariable Long doctorId, @RequestParam  LocalDate date){
        List<SlotResponseDto> slots = slotService.getAvailableSlots(doctorId, date);
        return ResponseEntity.ok(slots);
    }

    @DeleteMapping("/{doctorId}/slots/{slotId}")
    public ResponseEntity<?> deleteSlot(@PathVariable Long doctorId, @PathVariable Long slotId){
        slotService.deleteSlot(doctorId, slotId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/slots/{slotId}/book")
    public ResponseEntity<SlotResponseDto> bookSlot(@PathVariable Long slotId){
        return ResponseEntity.ok(slotService.bookSlot(slotId));
    }

    @PatchMapping("/slots/{slotId}/cancel")
    public ResponseEntity<SlotResponseDto> cancelSlot(@PathVariable Long slotId, @RequestBody @Valid CancelSlotDto dto) {
        return ResponseEntity.ok(slotService.cancelSlot(slotId, dto));
    }
}
