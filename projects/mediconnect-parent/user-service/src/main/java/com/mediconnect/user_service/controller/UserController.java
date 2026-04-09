package com.mediconnect.user_service.controller;

import com.mediconnect.user_service.dto.CreateUserDto;
import com.mediconnect.user_service.dto.LoginDto;
import com.mediconnect.user_service.dto.UpdateUserDto;
import com.mediconnect.user_service.dto.UserResponseDto;
import com.mediconnect.user_service.entity.Role;
import com.mediconnect.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserDto dto){
        UserResponseDto created = userService.registerUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> getDoctors() {
        return ResponseEntity.ok(userService.getDoctors());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Long userId, @RequestBody @Valid UpdateUserDto dto){
        return ResponseEntity.ok(userService.updateUserDetails(userId, dto));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getRole(@PathVariable Role role){
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto dto){
        return ResponseEntity.ok(userService.login(dto));
    }
}
