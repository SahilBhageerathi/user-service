package com.rideshare.platform.user_service.controller;

import com.rideshare.platform.user_service.dto.*;
import com.rideshare.platform.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@Valid @RequestBody UserRegistrationDto request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }

        UserDto response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response));
    }

    @PutMapping("/auth/update/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserUpdateDto request,BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }

        UserDto response = userService.updateUser(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @GetMapping("/auth/getOtp")
    public ResponseEntity<ApiResponse<UserOtpResponseDto>> getOtp(@Valid @RequestBody UserOtpRequestDto request, BindingResult result) {

        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            ApiError error = new ApiError("Validation failed", errors);
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        }
       UserOtpResponseDto response = userService.generateOTP(request);
        return ResponseEntity.status(200).body(new ApiResponse<>(response));
    }

    @GetMapping("/test")
    public String test() {
        return "TEST API IS RUNNING";
    }
}
