package com.mediconnect.doctor_service.service.doctor;

import com.mediconnect.doctor_service.client.UserClient;
import com.mediconnect.doctor_service.dto.doctor.CreateDoctorDto;
import com.mediconnect.doctor_service.dto.doctor.DoctorResponseDto;
import com.mediconnect.doctor_service.dto.doctor.UpdateDoctorDto;
import com.mediconnect.doctor_service.dto.doctor.UserResponse;
import com.mediconnect.doctor_service.entity.DoctorProfile;
import com.mediconnect.doctor_service.entity.Role;
import com.mediconnect.doctor_service.exceptions.ResourceAlreadyExistsException;
import com.mediconnect.doctor_service.exceptions.ResourceNotFoundException;
import com.mediconnect.doctor_service.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor

public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserClient userClient;

    @Override
    public DoctorResponseDto createDoctor(CreateDoctorDto dto) {
        if(doctorRepository.existsByUserId(dto.getUserId())) {
            throw new ResourceAlreadyExistsException("doctor with userId already exists.");
        }

        UserResponse user =  userClient.getUserById(dto.getUserId());
        System.out.println("Role received: " + user.getRole());
        if(!user.isActive()){
            throw new ResourceNotFoundException("user is not active.");
        }

        if(user.getRole() != Role.DOCTOR){
            throw new IllegalStateException("user is not in DOCTOR role");
        }

        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setUserId(dto.getUserId());
        doctorProfile.setSpecialization(dto.getSpecialization());
        doctorProfile.setFee(dto.getFee());
        doctorProfile.setActive(dto.isActive());
        DoctorProfile doctor = doctorRepository.save(doctorProfile);
        return mapToDoctorResponseDto(doctor);

    }

    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        List<DoctorProfile> doctors = doctorRepository.findAll();

        return doctors.stream().map(this::mapToDoctorResponseDto).collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDto getDoctorById(Long doctorId) {
        DoctorProfile doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor with id " + doctorId + " does not exist."));
        return mapToDoctorResponseDto(doctor);
    }

    @Override
    public DoctorResponseDto updateDoctor(Long doctorId, UpdateDoctorDto dto) {
        DoctorProfile doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor with id " + doctorId + " does not exist."));

        if(!doctor.isActive()) {
            throw new IllegalStateException("doctor is not active.");
        }

        doctor.setSpecialization(dto.getSpecialization());
        doctor.setFee(dto.getFee());
        doctorRepository.save(doctor);
        return mapToDoctorResponseDto(doctor);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        DoctorProfile doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("doctor with id " + doctorId + " does not exist."));

        if(!doctor.isActive()) {
            throw new IllegalStateException("doctor is not active.");
        }
        doctor.setActive(false);
        doctorRepository.save(doctor);
    }


    //helper method
    private DoctorResponseDto mapToDoctorResponseDto(DoctorProfile doctorProfile) {
        return DoctorResponseDto.builder()
                .id(doctorProfile.getId())
                .userId(doctorProfile.getUserId())
                .specialization( doctorProfile.getSpecialization() )
                .fee(doctorProfile.getFee())
                .createdAt(doctorProfile.getCreatedAt())
                .updatedAt(doctorProfile.getUpdatedAt())
                .build();
    }

}
