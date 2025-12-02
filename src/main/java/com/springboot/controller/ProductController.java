package com.springboot.controller;

import com.springboot.dto.*;
import com.springboot.service.MemberService;
import com.springboot.service.ProductService;
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
public class ProductController {

    private  final ProductService productService;
    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request){

        return  productService.create(request);
    }

    @GetMapping
    public Page<ProductResponse> getAll(Pageable pageable) {

        return productService.getAll(pageable);
    }

    @GetMapping("/search")
    public Page<ProductResponse> search (
            ProductSearchCond cond,
            Pageable pageable
    ){
        return productService.search(cond, pageable);
    }

}
