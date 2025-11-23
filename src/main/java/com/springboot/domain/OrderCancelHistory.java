package com.springboot.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.Order;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class OrderCancelHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders order;

    private  String canceledBy;
    private  String cancelReason;

    private LocalDateTime cancelAt;

    public OrderCancelHistory(Orders order, String canceledBy, String cancelReason) {
        this.order = order;
        this.canceledBy = canceledBy;
        this.cancelReason = cancelReason;
        this.cancelAt = LocalDateTime.now();
    }
}
