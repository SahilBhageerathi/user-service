package com.rideshare.platform.user_service.utils.customError;

public class InvalidOtpException extends RuntimeException{
    public InvalidOtpException(String message){
        super(message);
    }
}
