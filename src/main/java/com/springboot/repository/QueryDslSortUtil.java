package com.springboot.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

public class QueryDslSortUtil {
    public  static OrderSpecifier<?>[] toOrderSpecifiers(Sort sort, Object target) {
        return sort.stream().map(order-> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            PathBuilder<?> entityPath = new PathBuilder<>(target.getClass(), target.toString());

            return new OrderSpecifier(direction, entityPath.get(property));
        }).toArray(OrderSpecifier[]::new);
    }


}
