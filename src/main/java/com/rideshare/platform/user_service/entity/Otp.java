package com.rideshare.platform.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String otp;

    private Date expiresAt;

    private int attempts = 0;

    private Date lastAttempt = new Date();


    public Otp(String userId, String otp, Date expiresAt) {
        this.userId = userId;
        this.otp = otp;
        this.expiresAt = expiresAt;
        this.attempts = 0;
        this.lastAttempt = new Date();
    }

}
