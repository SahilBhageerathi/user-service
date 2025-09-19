package com.rideshare.platform.user_service.utils.customError;

public class EmailAlreadyInUseException extends RuntimeException{
   public EmailAlreadyInUseException(String message){
        super(message);
    }
}
