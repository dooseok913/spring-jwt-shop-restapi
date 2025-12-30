package com.springboot.dto;

import com.springboot.domain.Member;
import com.springboot.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
                           @Schema(example = "3", description = "상품 ID")
                           @NotNull Long productId,

                           @Schema(example = "2", description = "구매 수량(최소 1)")
                           @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") int quantity
                           ) {
}
