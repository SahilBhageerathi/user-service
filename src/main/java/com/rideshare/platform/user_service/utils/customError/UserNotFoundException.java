package com.rideshare.platform.user_service.utils.customError;

public class UserNotFoundException extends RuntimeException{
   public UserNotFoundException(String message){
        super(message);
    }
}
