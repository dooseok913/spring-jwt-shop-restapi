package com.springboot.controller;

import com.springboot.dto.*;
import com.springboot.service.MemberService;
import com.springboot.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name="상품 API", description = "상품 조회*검색*등록")
public class ProductController {

    private  final ProductService productService;

    @Operation(summary = "상품 등록")
    @PostMapping
    public ApiResponse<ProductResponse> create(@RequestBody ProductRequest request){

        return  ApiResponse.ok(productService.create(request));
    }

    @Operation(summary = "상품 전체 목록 조회")
    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAll(@Parameter(hidden = true) Pageable pageable) {

        return ApiResponse.ok(productService.getAll(pageable));
    }

    @Operation(summary = "상품 검색", description = "이름/최소가격/최대가격으로 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<Page<ProductResponse>> search (
            ProductSearchCond cond,
            @Parameter(hidden = true) Pageable pageable
    ){
        return ApiResponse.ok(productService.search(cond, pageable));
    }

}
