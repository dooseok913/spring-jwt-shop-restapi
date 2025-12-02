package com.springboot.repository;


import com.springboot.domain.Product;
import com.springboot.dto.ProductSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> search (ProductSearchCond cond, Pageable pageable);


}
