package com.rideshare.platform.user_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {
    private String message;
    private List<String> errors;

    public ApiError(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
