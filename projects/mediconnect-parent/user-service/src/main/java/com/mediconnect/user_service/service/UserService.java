package com.mediconnect.user_service.service;

import com.mediconnect.user_service.dto.*;
import com.mediconnect.user_service.entity.Role;
import com.mediconnect.user_service.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(CreateUserDto dto);

    UserResponseDto getUserByUserId(Long userId);

    List<UserResponseDto> getDoctors();

    UserResponseDto updateUserDetails(Long userId, UpdateUserDto dto);

    List<UserResponseDto> getUsersByRole(Role role);

    void deleteUser(Long userId);

    List<UserResponseDto> getUsers();

    List<UserResponseDto> getActiveUsers();

    UserResponseDto getUserByEmail(String email);

    LoginResponseDto login(@Valid LoginDto dto);
}
