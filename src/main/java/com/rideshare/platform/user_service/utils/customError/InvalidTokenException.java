package com.rideshare.platform.user_service.utils.customError;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message){
        super(message);
    }
}
