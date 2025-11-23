package com.springboot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //회원관리
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."),
    DUPLICATE_MEMBER(HttpStatus.BAD_REQUEST, "이미 존재하는 회원 입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    //상품관리
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),

    //주문관리
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    ORDER_CANNOT_CANCEL(HttpStatus.NOT_FOUND, "해당 주문은 취소할 수 없습니다."),
    ORDER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "본인의 주문만 취소할 수 있습니다."),

    //JWT
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "JW 토큰이 만료되었습니다."),
    JWT_MISSING(HttpStatus.UNAUTHORIZED, "JWT 토큰이 없습니다."),

    //세션 내부 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public  int getStatus() {
        return httpStatus.value();
    }


}
