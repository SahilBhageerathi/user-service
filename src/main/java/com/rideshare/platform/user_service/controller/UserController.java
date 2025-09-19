package com.rideshare.platform.user_service.controller;

import com.rideshare.platform.user_service.dto.ApiError;
import com.rideshare.platform.user_service.dto.ApiResponse;
import com.rideshare.platform.user_service.dto.UserDto;
import com.rideshare.platform.user_service.dto.UserRegistrationDto;
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

    @GetMapping("/test")
    public String test(){
        return "TEST API IS RUNNING";
    }


//    // 2. Login API usually handled by Spring Security with JWT filter
//
//    // 3. Get Current User Profile
//    @GetMapping("/users/me")
//    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//        UserDto userDto = userService.getUserByEmail(userDetails.getUsername());
//        return ResponseEntity.ok(userDto);
//    }
//
//    // 4. Update Current User Profile
//    @PutMapping("/users/me")
//    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetails userDetails,
//                                        @RequestBody UserDto userDto) {
//        userService.updateUser(userDetails.getUsername(), userDto);
//        return ResponseEntity.ok("User updated successfully");
//    }
//
//    // 5. Admin: List pending drivers
//    @GetMapping("/admin/users/pending")
//    public ResponseEntity<List<UserDto>> getPendingDrivers() {
//        List<UserDto> pendingDrivers = userService.getPendingDrivers();
//        return ResponseEntity.ok(pendingDrivers);
//    }
//
//    // 6. Admin: Approve driver
//    @PutMapping("/admin/users/{id}/approve")
//    public ResponseEntity<?> approveDriver(@PathVariable Long id) {
//        userService.approveDriver(id);
//        return ResponseEntity.ok("Driver approved successfully");
//    }
}
