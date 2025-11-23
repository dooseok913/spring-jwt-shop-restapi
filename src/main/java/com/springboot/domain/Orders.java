package com.springboot.domain;

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
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
        if (this.status == OrderStatus.SHIPPED) {
            throw  new IllegalStateException("배송이 시작된 주문은 취소할 수 없습니다.");
        }
        if (this.status == OrderStatus.DELIVERED) {
            throw  new IllegalStateException("배송 완료된 주문은 취소할 수 없습니다.");
        }
    }
    public void cancel() {
        this.status = OrderStatus.CANCELED;
        this.orderAt = LocalDateTime.now();
    }

}
