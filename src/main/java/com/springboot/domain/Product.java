package com.springboot.domain;


import com.springboot.exception.CustomException;
import com.springboot.exception.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String name;

    private  int price;

    private  int stock;



    private LocalDateTime createdAt;


    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = LocalDateTime.now();
    }
    public  void reduceStock(int quantity){
        if(this.stock < quantity) {
            throw new CustomException(ErrorCode.OUT_OF_STOCK);
        }
        this.stock -= quantity;
    }

    public  void addStock(int quantity) {
        this.stock += quantity;
    }





}
