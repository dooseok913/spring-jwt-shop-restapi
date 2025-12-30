package com.springboot.dto;

import com.springboot.domain.OrderCancelHistory;
import com.springboot.domain.OrderStatus;
import com.springboot.domain.Orders;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record OrderCancelResponse(
        @Schema(example = "10") Long orderId,
        @Schema(example = "CANCELED") OrderStatus status,
        @Schema(example = "주문이 정상 취소되었습니다.") String message,
        @Schema(example = "2025-01-01T12:30:00") LocalDateTime cancelAt
) { public static OrderCancelResponse from(Orders order) {
    return  new OrderCancelResponse(
            order.getId(),
            order.getStatus(),
            "주문이 정상 취소되었습니다.",
            order.getOrderAt()
    );
}

}
