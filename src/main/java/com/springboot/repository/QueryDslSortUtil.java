package com.springboot.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

public class QueryDslSortUtil {

    public static OrderSpecifier<?>[] toOrderSpecifiers(
            Sort sort,
            PathBuilder<?> entityPath
    ) {
        return sort.stream()
                .map(order -> {
                    Order direction =
                            order.isAscending() ? Order.ASC : Order.DESC;

                    // ✅ 핵심 수정 포인트
                    return new OrderSpecifier<>(
                            direction,
                            entityPath.getComparable(order.getProperty(), Comparable.class)
                    );
                })
                .toArray(OrderSpecifier[]::new);
    }
}
