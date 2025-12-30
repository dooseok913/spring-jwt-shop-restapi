package com.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderCancelRequest(
        @Schema(example = "상품이 마음에 들지 않음", description = "취소 사유")
        String reason) {
}
