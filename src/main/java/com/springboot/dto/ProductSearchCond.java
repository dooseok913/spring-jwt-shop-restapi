package com.springboot.dto;


// 검색 조건 DTO
public record ProductSearchCond(
        String name,  //상품 이름 부분 검색
        Integer minPrice, // 최소 가격
        Integer maxPrice // 최대 가격
) {
}
