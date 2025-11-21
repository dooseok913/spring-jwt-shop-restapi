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


}
