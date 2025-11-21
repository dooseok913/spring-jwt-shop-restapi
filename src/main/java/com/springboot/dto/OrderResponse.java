package com.springboot.dto;

import com.springboot.domain.OrderStatus;
import com.springboot.domain.Orders;

import java.time.LocalDateTime;

public record OrderResponse(Long orderId,
                            String productName,
                            int quantity,
                            int totalPrice,
                            LocalDateTime orderAt,
                            OrderStatus status
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
