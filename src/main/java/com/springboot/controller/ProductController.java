package com.springboot.controller;

import com.springboot.dto.MemberRequest;
import com.springboot.dto.MemberResponse;
import com.springboot.dto.ProductRequest;
import com.springboot.dto.ProductResponse;
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

}
