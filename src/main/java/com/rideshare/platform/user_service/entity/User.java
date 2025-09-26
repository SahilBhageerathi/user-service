package com.rideshare.platform.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users",schema = "Public")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true, name = "phone_number")
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean approved; // Only for drivers

    @Column(length = 512)
    private String refreshToken;

    //TODO Add location field later if needed
}