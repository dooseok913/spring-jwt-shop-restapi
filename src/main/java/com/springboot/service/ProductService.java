package com.springboot.service;


import com.springboot.domain.Product;
import com.springboot.dto.OrderResponse;
import com.springboot.dto.ProductRequest;
import com.springboot.dto.ProductResponse;
import com.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse create(ProductRequest request){
        Product product = new Product(request.name(), request.price(), request.stock());
        Product saved = productRepository.save(product);
        return  ProductResponse.from(saved);
    }

    public Page<ProductResponse> getAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return  products.map(ProductResponse::from);
    }
}
