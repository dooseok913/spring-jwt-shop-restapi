package com.springboot.domain;

import com.springboot.exception.CustomException;
import com.springboot.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;



    @ManyToOne(fetch = FetchType.LAZY)
    private  Product product;

    private int quantity;

    private  int totalPrice;

    private LocalDateTime orderAt;

    private LocalDateTime canceledAt;

    public Orders(Member member, Product product, int quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice()*quantity;
        this.orderAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void validateCancelable() {
        if (this.status == OrderStatus.CANCELED) {
            throw new CustomException(ErrorCode.ORDER_CANNOT_CANCEL);
        }
        if (this.status == OrderStatus.SHIPPED) {
            throw  new CustomException(ErrorCode.ORDER_CANNOT_CANCEL);
        }
        if (this.status == OrderStatus.DELIVERED) {
            throw  new CustomException(ErrorCode.ORDER_CANNOT_CANCEL);
        }
    }
    public void cancel() {
        this.status = OrderStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();
    }

}
