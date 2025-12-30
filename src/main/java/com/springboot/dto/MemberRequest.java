package com.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberRequest(
        @Schema(example = "user01", description = "로그인에 사용될 사용자 ID") String username,
        @Schema(example = "1234", description = "비밀번호") String password,
        @Schema(example = "홍길동", description = "사용자 이름") String name,
        @Schema(example = "test@test.com", description = "사용자 이메일") String email
                            ) {
}
