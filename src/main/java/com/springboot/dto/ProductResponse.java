package com.springboot.dto;

import com.springboot.domain.Product;

import java.time.LocalDateTime;

public record ProductResponse(Long id,
                              String name,
                              int price,
                              int stock,
                              String status,
                              LocalDateTime createdAT) {
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
