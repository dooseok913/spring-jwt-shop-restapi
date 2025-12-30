package com.springboot.dto;

import com.springboot.domain.OrderCancelHistory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record OrderCancelHistoryResponse(
            @Schema(example = "1") Long id,
            @Schema(example = "10") Long orderId,
            @Schema(example = "user01") String canceledBy,
            @Schema(example = "단순 변심") String cancelReason,
            @Schema(example = "2025-01-01T12:30:00")LocalDateTime cancelAt

) { public static OrderCancelHistoryResponse from(OrderCancelHistory history) {
    return new OrderCancelHistoryResponse(
        history.getId(),
                history.getOrder().getId(),
                history.getCanceledBy(),
                history.getCancelReason(),
                history.getCancelAt()
    );
}
}
