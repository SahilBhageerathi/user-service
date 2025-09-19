package com.rideshare.platform.user_service.service.impl;

import com.rideshare.platform.user_service.dto.UserDto;
import com.rideshare.platform.user_service.dto.UserRegistrationDto;
import com.rideshare.platform.user_service.entity.User;
import com.rideshare.platform.user_service.repository.UserRepository;
import com.rideshare.platform.user_service.service.UserService;
import com.rideshare.platform.user_service.utils.customError.EmailAlreadyInUseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDto registerUser(UserRegistrationDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        // 2. Map DTO to entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        // 3. Hash the password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 4. Save to DB
        User savedUser = userRepository.save(user);

        // 5. Map entity to DTO to return
        UserDto dto = new UserDto();
        dto.setId(savedUser.getId());
        dto.setName(savedUser.getName());
        dto.setEmail(savedUser.getEmail());
        dto.setPhoneNumber(savedUser.getPhoneNumber());
        dto.setRole(savedUser.getRole());

        return dto;
    }
}
