package com.springboot.dto;

import com.springboot.domain.OrderStatus;
import com.springboot.domain.Orders;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record OrderResponse(
                            @Schema(example = "10") Long orderId,
                            @Schema(example = "아이폰 15") String productName,
                            @Schema(example = "2") int quantity,
                            @Schema(example = "2400000") int totalPrice,
                            @Schema(example = "2025-01-01T12:00:00") LocalDateTime orderAt,
                            @Schema(example = "CREATED") OrderStatus status
                            ) {
    public static OrderResponse from(Orders order){
        return new OrderResponse(
                order.getId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderAt(),
                order.getStatus()
        );
    }
}
