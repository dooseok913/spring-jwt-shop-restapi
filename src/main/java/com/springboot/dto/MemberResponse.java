package com.springboot.dto;

import com.springboot.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MemberResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "user01") String username,
        @Schema(example = "홍길동") String name,
        @Schema(example = "user01@test.com") String email,
        @Schema(example = "2025-01-01T10:20:30") LocalDateTime createdAt) {
    public static MemberResponse from(Member member){
        return  new MemberResponse(member.getId(),
                member.getUsername(),
                member.getName(),
                                    member.getEmail(),
                member.getCreatedAt()
        );
    }
}
