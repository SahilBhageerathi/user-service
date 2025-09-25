package com.rideshare.platform.user_service.controller;

import com.rideshare.platform.user_service.dto.*;
import com.rideshare.platform.user_service.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@Valid @RequestBody UserRegistrationDto request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }

        UserDto response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserUpdateDto request,BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }

        UserDto response = userService.updateUser(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> response = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable("id") Long id) {
        UserDto response = userService.getUserById(id);
        return ResponseEntity.status(200).body(new ApiResponse<>(response));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> deleteUser(@PathVariable("id") Long id) {
        UserDto response = userService.deleteUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @PostMapping("/auth/otp")
    public ResponseEntity<ApiResponse<UserOtpResponseDto>> generateOtp(@Valid @RequestBody UserOtpRequestDto request, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
        UserOtpResponseDto response = userService.generateOTP(request);
        return ResponseEntity.status(200).body(new ApiResponse<>(response));
    }


    @PostMapping("/auth/otp/verify")
    public ResponseEntity<ApiResponse<UserVerifyOtpResponseDto>> verifyOtp(@RequestBody @Valid UserVerifyOtpRequestDto request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
        UserVerifyOtpResponseDto response = userService.verifyOTP(request);
        return ResponseEntity.status(200).body(new ApiResponse<>(response));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ApiResponse<UserVerifyOtpResponseDto>> getRefreshToken(@RequestBody UserRefreshTokenRequestDto request,BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
        UserVerifyOtpResponseDto response = userService.generateNewRefreshToken(request);
        return ResponseEntity.status(200).body(new ApiResponse<>(response));
    }


    @GetMapping("/test")
    public String test() {
        return "TEST API IS RUNNING";
    }
}
