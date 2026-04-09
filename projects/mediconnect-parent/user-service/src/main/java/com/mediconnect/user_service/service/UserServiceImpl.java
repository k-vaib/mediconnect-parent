package com.mediconnect.user_service.service;

import com.mediconnect.user_service.dto.*;
import com.mediconnect.user_service.entity.Role;
import com.mediconnect.user_service.entity.User;
import com.mediconnect.user_service.exceptions.InvalidCredentialsException;
import com.mediconnect.user_service.exceptions.ResourceAlreadyExists;
import com.mediconnect.user_service.exceptions.ResourceNotFoundException;
import com.mediconnect.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDto registerUser(CreateUserDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExists("User already exists with this email");
        }
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setUserRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return mapToUserResponseDto(savedUser);

    }


    @Override
    public UserResponseDto getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with specified id does not exist."));

        return mapToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getDoctors() {
        List<User> doctors = userRepository.findByUserRole(Role.DOCTOR);

        return doctors.stream().map(this::mapToUserResponseDto).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUserDetails(Long userId, UpdateUserDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with specified id does not exist."));

        if(dto.getUserName() != null){
            user.setUserName(dto.getUserName());
        }
        if(dto.getPhone() != null){
            user.setPhone(dto.getPhone());
        }

        User savedUser = userRepository.save(user);
        return  mapToUserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getUsersByRole(Role role) {
        List<User> users = userRepository.findByUserRole(role);

        return users.stream().map(this::mapToUserResponseDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with specified id does not exist."));

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getActiveUsers() {
        List<User> users = userRepository.getUsersByActiveTrue();
        return users.stream().map(this::mapToUserResponseDto).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with specified email does not exist."));

        return mapToUserResponseDto(user);
    }

    @Override
    public LoginResponseDto login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User with specified email does not exist."));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Credentials.");
        }

        return LoginResponseDto.builder()
                .userId(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .role(user.getUserRole().name())
                .message("Login successful")
                .build();

    }


    //helper method
    private UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .role(user.getUserRole().name())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
