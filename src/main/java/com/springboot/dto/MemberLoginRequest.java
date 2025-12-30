package com.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberLoginRequest(
        @Schema(example = "user01") String username,
        @Schema(example = "1234") String password) {
}
