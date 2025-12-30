package com.springboot.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.PathBuilder;
import com.springboot.domain.Product;
import com.springboot.domain.QProduct;
import com.springboot.dto.ProductSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<Product> search(ProductSearchCond cond, Pageable pageable) {

        QProduct product = QProduct.product;

        // ✅ 핵심: PathBuilder는 엔티티 + alias 문자열
        PathBuilder<Product> entityPath =
                new PathBuilder<>(Product.class, "product");

        BooleanBuilder builder = new BooleanBuilder();

        // 이름 검색
        if (cond.name() != null && !cond.name().isBlank()) {
            builder.and(product.name.containsIgnoreCase(cond.name()));
        }

        // 가격 범위 검색
        if (cond.minPrice() != null) {
            builder.and(product.price.goe(cond.minPrice()));
        }

        if (cond.maxPrice() != null) {
            builder.and(product.price.loe(cond.maxPrice()));
        }

        List<Product> content = query
                .select(product)
                .from(product)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                // ✅ 여기서만 orderBy
                .orderBy(
                        QueryDslSortUtil.toOrderSpecifiers(
                                pageable.getSort(),
                                entityPath
                        )
                )
                .fetch();

        long total = query
                .select(product.count())
                .from(product)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
