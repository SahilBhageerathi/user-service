package com.rideshare.platform.user_service.repository;

import com.rideshare.platform.user_service.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUserId(String userId);
    void deleteByUserId(String userId);
}
