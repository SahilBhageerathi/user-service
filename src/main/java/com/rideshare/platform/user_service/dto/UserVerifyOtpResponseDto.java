package com.rideshare.platform.user_service.dto;


import com.rideshare.platform.user_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVerifyOtpResponseDto {
    private String message;
    private String accessToken;
    private String refreshToken;
    private String name;
    private String userId;
    private String email;
    private String phoneNumber;
    private Role role;
}