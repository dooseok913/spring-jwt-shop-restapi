package com.springboot.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustom(CustomException ex) {
        ErrorCode code =ex.getErrorCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiErrorResponse.of(code));
    }

    //@Valid validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValid(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("INVALID_INPUT", message, 400));
    }

    //JWT 인증 실패
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity
                .status(401)
                .body(ApiErrorResponse.of(ErrorCode.EXPIRED_JWT));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiErrorResponse> handleJwt(JwtException ex) {
        return ResponseEntity
                .status(401)
                .body(ApiErrorResponse.of(ErrorCode.INVALID_JWT));
    }

    //IllegalArgument & IllegalState
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiErrorResponse> handleIllegal(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("BAD_REQUEST", ex.getMessage(), 400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(ApiErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
