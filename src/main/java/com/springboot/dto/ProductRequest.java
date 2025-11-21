package com.springboot.dto;

public record ProductRequest(String name,
                             int price,
                             int stock) {
}
