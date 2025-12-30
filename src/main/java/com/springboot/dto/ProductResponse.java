package com.springboot.dto;

import com.springboot.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ProductResponse(
                              @Schema(example = "3") Long id,
                              @Schema(example = "아이폰 15") String name,
                              @Schema(example = "1200000") int price,
                              @Schema(example = "50") int stock,
                              @Schema(example = "판매중 / 품절") String status,
                              @Schema(example = "2025-01-01T10:20:30") LocalDateTime createdAT) {
    public static ProductResponse from(Product product) {
        String status = product.getStock() == 0 ? "품절":"판매중";
        return  new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                status,
                product.getCreatedAt()

        );
    }
}
