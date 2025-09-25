package com.rideshare.platform.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyOtpRequestDto {
    @NotNull(message = "UserId is required")
    Long userId;

    @NotBlank(message = "otp is required")
    String otp;
}
