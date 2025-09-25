package com.rideshare.platform.user_service.config;


import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvLoader {

    public static Dotenv dotenv;

    @PostConstruct
    public void init() {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        setSystemProperty("TWILIO_ACCOUNT_SID");
        setSystemProperty("TWILIO_AUTH_TOKEN");
        setSystemProperty("TWILIO_FROM_NUMBER");
        setSystemProperty("JWT_ACCESS_SECRET");
        setSystemProperty("JWT_REFRESH_SECRET");
    }

    private void setSystemProperty(String key) {
        String value = dotenv.get(key);
        if (value == null) value = System.getenv(key);
        if (value != null) System.setProperty(key, value);
    }
}


