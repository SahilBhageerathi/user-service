package com.rideshare.platform.user_service.utils;

import com.rideshare.platform.user_service.entity.Otp;
import com.rideshare.platform.user_service.repository.OtpRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Component
public class OtpUtils {

    @Autowired
    private OtpRepository otpRepository;

    @Value("${otp.expiry.minutes:5}")
    private int otpExpiryMinutes;

    @Value("${otp.max.attempts:3}")
    private int otpMaxAttempts;

    @Value("${otp.attempt.interval.minutes:5}")
    private int otpAttemptIntervalMinutes;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;


    @PostConstruct
    private void initTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }

    public void storeOTP(String userId, String otp) {
        Date expiresAt = new Date(System.currentTimeMillis() + otpExpiryMinutes * 60 * 1000L);
        Optional<Otp> existing = otpRepository.findByUserId(userId);
        Otp otpEntity;
        if (existing.isPresent()) {
            otpEntity = existing.get();
            otpEntity.setOtp(otp);
            otpEntity.setExpiresAt(expiresAt);
            otpEntity.setAttempts(0);
            otpEntity.setLastAttempt(new Date());
        } else {
            otpEntity = new Otp(userId, otp, expiresAt);
        }
        otpRepository.save(otpEntity);
    }

    public boolean verifyOTP(String userId, String otp) {
        Optional<Otp> storedOtpOpt = otpRepository.findByUserId(userId);
        if (storedOtpOpt.isEmpty()) return false;

        Otp storedOtp = storedOtpOpt.get();

        if (new Date().after(storedOtp.getExpiresAt())) return false;

        return storedOtp.getOtp().equals(otp);
    }

    public boolean canAttempt(String userId) {
        Optional<Otp> storedOtpOpt = otpRepository.findByUserId(userId);
        if (storedOtpOpt.isEmpty()) return false;

        Otp otpData = storedOtpOpt.get();

        Date now = new Date();
        Date lastAttempt = otpData.getLastAttempt();

        if (otpData.getAttempts() >= otpMaxAttempts) {
            long diffMillis = now.getTime() - lastAttempt.getTime();
            long intervalMillis = otpAttemptIntervalMinutes * 60 * 1000L;
            if (diffMillis < intervalMillis) {
                return false;
            } else {
                otpData.setAttempts(0);
                otpData.setLastAttempt(now);
                otpRepository.save(otpData);
                return true;
            }
        }
        return true;
    }

    public int incrementAttempt(String userId) {
        Optional<Otp> storedOtpOpt = otpRepository.findByUserId(userId);
        if (storedOtpOpt.isEmpty()) return 0;

        Otp otpData = storedOtpOpt.get();
        otpData.setAttempts(otpData.getAttempts() + 1);
        otpData.setLastAttempt(new Date());
        otpRepository.save(otpData);

        return otpData.getAttempts();
    }

    public void clearOTP(String userId) {
        otpRepository.deleteByUserId(userId);
    }

    public int getOtpMaxAttempts() {
        return otpMaxAttempts;
    }
}
