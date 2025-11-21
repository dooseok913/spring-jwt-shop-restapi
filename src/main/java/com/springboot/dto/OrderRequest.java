package com.springboot.dto;

import com.springboot.domain.Member;
import com.springboot.domain.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(@NotNull  Long memberId,
                           @NotNull Long productId,
                           @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") int quantity
                           ) {
}
