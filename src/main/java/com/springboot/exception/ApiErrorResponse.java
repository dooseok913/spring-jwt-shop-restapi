package com.springboot.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiErrorResponse(
        boolean success,
        String code,
        String message,
        int status,
        LocalDateTime timestamp

) {
    public  static ApiErrorResponse of(ErrorCode errorCode){
        return ApiErrorResponse.builder()
                .success(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static ApiErrorResponse of(String code, String message, int status) {
        return  ApiErrorResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
