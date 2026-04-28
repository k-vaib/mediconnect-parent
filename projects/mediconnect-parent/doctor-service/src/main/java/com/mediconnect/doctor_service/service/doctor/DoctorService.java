package com.mediconnect.doctor_service.service.doctor;

import com.mediconnect.doctor_service.dto.doctor.CreateDoctorDto;
import com.mediconnect.doctor_service.dto.doctor.DoctorResponseDto;
import com.mediconnect.doctor_service.dto.doctor.UpdateDoctorDto;

import java.util.List;

public interface DoctorService {
    DoctorResponseDto createDoctor(CreateDoctorDto dto);

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto getDoctorById(Long doctorId);

    DoctorResponseDto updateDoctor(Long doctorId, UpdateDoctorDto dto);

    void deleteDoctor(Long doctorId);
}
