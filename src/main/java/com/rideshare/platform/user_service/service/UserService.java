package com.rideshare.platform.user_service.service;

import com.rideshare.platform.user_service.dto.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserService {
    UserDto  registerUser(UserRegistrationDto request);
    UserDto  updateUser(Long userId,UserUpdateDto request);
    UserOtpResponseDto generateOTP(UserOtpRequestDto request);
    UserVerifyOtpResponseDto verifyOTP(UserVerifyOtpRequestDto request);
    UserVerifyOtpResponseDto generateNewRefreshToken(UserRefreshTokenRequestDto request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto  deleteUserById(Long id);
    ResponseEntity<UserDto> findUserByPhoneNumber(String phoneNumber);
}
