package com.springboot.dto;


import io.swagger.v3.oas.annotations.media.Schema;

// 검색 조건 DTO
public record ProductSearchCond(
        @Schema(example = "아이폰") String name,  //상품 이름 부분 검색
        @Schema(example = "500000") Integer minPrice, // 최소 가격
        @Schema(example = "2000000") Integer maxPrice // 최대 가격
) {
}
