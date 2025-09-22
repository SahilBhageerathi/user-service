package com.rideshare.platform.user_service.dto;

import com.rideshare.platform.user_service.entity.Role;
import com.rideshare.platform.user_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;  // USER or DRIVER
    private String status; //ONLINE OR OFFLINE FOR THE DRIVER

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.status = "ONLINE";
        //TODO: this.status = user.getStatus()
    }
}
