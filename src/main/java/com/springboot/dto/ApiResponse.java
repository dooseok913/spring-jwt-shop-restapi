package com.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ApiResponse<T> (
    @Schema(description = "성공 여부") boolean success,
    @Schema(description = "응답 데이터") T data,
    @Schema(description = "오류 메시지") String error
){
        public  static <T> ApiResponse<T> ok(T data) {
            return  ApiResponse.<T>builder()
                    .success(true)
                    .data(data)
                    .error(null)
                    .build();
        }
        public  static <T> ApiResponse<T> error(String message) {
            return ApiResponse.<T>builder()
                    .success(false)
                    .data(null)
                    .error(message)
                    .build();
        }
}
