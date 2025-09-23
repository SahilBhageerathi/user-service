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
    }

    private void setSystemProperty(String key) {
        String value = dotenv.get(key);                  // value from .env if present
        if (value == null) value = System.getenv(key);   // fallback to OS/Render env
        if (value != null) System.setProperty(key, value);
    }
}


//@Component
//public class EnvLoader {
//
//    public static Dotenv dotenv;
//
//    @PostConstruct
//    public void init() {
//        Dotenv dotenv = Dotenv.load();
//        System.setProperty("TWILIO_ACCOUNT_SID", dotenv.get("TWILIO_ACCOUNT_SID"));
//        System.setProperty("TWILIO_AUTH_TOKEN", dotenv.get("TWILIO_AUTH_TOKEN"));
//        System.setProperty("TWILIO_FROM_NUMBER", dotenv.get("TWILIO_FROM_NUMBER"));
////        System.setProperty("POSTGRES_DB_PASSWORD", dotenv.get("POSTGRES_DB_PASSWORD"));
////        System.setProperty("POSTGRES_DB_USER", dotenv.get("POSTGRES_DB_USER"));
////        System.setProperty("POSTGRES_DB_URL", dotenv.get("POSTGRES_DB_URL"));
//    }
//}


