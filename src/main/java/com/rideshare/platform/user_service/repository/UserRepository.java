package com.rideshare.platform.user_service.repository;

import com.rideshare.platform.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
//    Optional<User> findByEmail(String email);


}
