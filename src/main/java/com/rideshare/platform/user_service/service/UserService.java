package com.rideshare.platform.user_service.service;

import com.rideshare.platform.user_service.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDto  registerUser(UserRegistrationDto request);
    UserDto  updateUser(Long userId,UserUpdateDto request);
    UserOtpResponseDto generateOTP(UserOtpRequestDto request);
    public ResponseEntity<UserDto> findUserByPhoneNumber(String phoneNumber);
}
