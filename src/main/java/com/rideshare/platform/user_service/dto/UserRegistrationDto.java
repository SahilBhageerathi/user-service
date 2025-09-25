package com.rideshare.platform.user_service.dto;

import com.rideshare.platform.user_service.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "phoneNumber is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Role must be specified")
    private Role role;

    public @NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Username is mandatory") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Username is mandatory") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Password is mandatory") @Size(min = 6, max = 100, message = "Password must be at least 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is mandatory") @Size(min = 6, max = 100, message = "Password must be at least 6 characters") String password) {
        this.password = password;
    }

    public @NotNull(message = "Role must be specified") Role getRole() {
        return role;
    }

    public void setRole(@NotNull(message = "Role must be specified") Role role) {
        this.role = role;
    }
}
