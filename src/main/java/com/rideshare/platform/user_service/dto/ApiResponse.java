package com.rideshare.platform.user_service.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public ApiResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public ApiResponse(ApiError error) {
        this.error = error;
        this.data = null;
    }
}
