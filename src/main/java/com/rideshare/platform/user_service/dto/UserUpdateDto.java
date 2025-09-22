package com.rideshare.platform.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    private String phoneNumber;

}
