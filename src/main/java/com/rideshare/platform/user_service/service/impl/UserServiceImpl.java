package com.rideshare.platform.user_service.service.impl;

import com.rideshare.platform.user_service.dto.*;
import com.rideshare.platform.user_service.entity.User;
import com.rideshare.platform.user_service.repository.UserRepository;
import com.rideshare.platform.user_service.service.UserService;
import com.rideshare.platform.user_service.utils.JwtUtils;
import com.rideshare.platform.user_service.utils.OtpUtils;
import com.rideshare.platform.user_service.utils.customError.EmailAlreadyInUseException;
import com.rideshare.platform.user_service.utils.customError.InvalidOtpException;
import com.rideshare.platform.user_service.utils.customError.InvalidTokenException;
import com.rideshare.platform.user_service.utils.customError.UserNotFoundException;

import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpUtils otpUtils;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpUtils otpUtils, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpUtils = otpUtils;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDto registerUser(UserRegistrationDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        user.setPassword(passwordEncoder.encode(request.getPassword()));


        User savedUser = userRepository.save(user);

        UserDto dto = new UserDto();
        dto.setId(savedUser.getId());
        dto.setName(savedUser.getName());
        dto.setEmail(savedUser.getEmail());
        dto.setPhoneNumber(savedUser.getPhoneNumber());
        dto.setRole(savedUser.getRole());

        return dto;
    }

    @Override
    public UserDto updateUser(Long userId, UserUpdateDto request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));


        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(request.getPhoneNumber());
        }


        User updatedUser = userRepository.save(existingUser);


        return UserDto.builder()
                .id(userId)
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .phoneNumber(updatedUser.getPhoneNumber())
                .role(updatedUser.getRole())
                .build();
    }

    public UserOtpResponseDto generateOTP(UserOtpRequestDto request) {
        User existingUser = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found with phone: " + request.getPhoneNumber()));


        try {
            String otp = otpUtils.generateOTP();
            otpUtils.storeOTP(existingUser.getId().toString(), otp);
            otpUtils.sendSms("+91" + request.getPhoneNumber(), "Otp from Ride Sharing system is " + otp);
            return UserOtpResponseDto.builder()
                    .userId(existingUser.getId().toString())
                    .email(existingUser.getEmail())
                    .phoneNumber(existingUser.getPhoneNumber())
                    .otp(otp)
                    .build();
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with phone: " + request.getPhoneNumber());
        }
    }

    @Override
    public UserVerifyOtpResponseDto verifyOTP(UserVerifyOtpRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isValid = otpUtils.verifyOTP(request.getUserId().toString(), request.getOtp());
        if (!isValid) throw new InvalidOtpException("Invalid OTP");



        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);


        return UserVerifyOtpResponseDto.builder()
                .message("Otp verified successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(user.getName())
                .userId(user.getId().toString())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public UserVerifyOtpResponseDto generateNewRefreshToken(UserRefreshTokenRequestDto request) {
        String token = request.getRefreshToken();
        Claims claims = jwtUtils.validateRefreshToken(token);
        Long userId = Long.parseLong(claims.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!token.equals(user.getRefreshToken())) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String newAccessToken = jwtUtils.generateAccessToken(user);
        return UserVerifyOtpResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(token)
                .name(user.getName())
                .userId(user.getId().toString())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserDto::new).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDto deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);

        return new UserDto(user);
    }

    @Override
    public ResponseEntity<UserDto> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(user -> ResponseEntity.ok(new UserDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
