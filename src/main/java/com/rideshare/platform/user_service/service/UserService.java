package com.rideshare.platform.user_service.service;

import com.rideshare.platform.user_service.dto.UserDto;
import com.rideshare.platform.user_service.dto.UserRegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto  registerUser(UserRegistrationDto request);
}
