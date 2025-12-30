package com.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductRequest(
        @Schema(example = "아이폰 15") String name,
        @Schema(example = "1200000") int price,
        @Schema(example = "50") int stock) {
}
