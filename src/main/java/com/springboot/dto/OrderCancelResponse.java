package com.springboot.dto;

import com.springboot.domain.OrderCancelHistory;
import com.springboot.domain.OrderStatus;
import com.springboot.domain.Orders;

import java.time.LocalDateTime;

public record OrderCancelResponse(
        Long orderId,
        OrderStatus status,
        String message,
        LocalDateTime cancelAt
) { public static OrderCancelResponse from(Orders order) {
    return  new OrderCancelResponse(
            order.getId(),
            order.getStatus(),
            "주문이 정상 취소되었습니다.",
            order.getOrderAt()
    );
}

}
