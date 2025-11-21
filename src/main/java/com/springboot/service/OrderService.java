package com.springboot.service;


import com.springboot.domain.Member;
import com.springboot.domain.OrderStatus;
import com.springboot.domain.Orders;
import com.springboot.domain.Product;
import com.springboot.dto.OrderRequest;
import com.springboot.dto.OrderResponse;
import com.springboot.repository.MemberRepository;
import com.springboot.repository.OrderRepository;
import com.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;




    @Transactional
    public OrderResponse create(String username, OrderRequest request){
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(()-> new RuntimeException("회원이 존재하지 않습니다."));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(()-> new RuntimeException("상품이 존재하지 않습니다."));

        if(product.getStock() < request.quantity()){
            throw new RuntimeException("재고가 부족합니다.");
        }

        product.setStock((product.getStock()-request.quantity()));

        Orders order = new Orders(member, product, request.quantity());
        Orders saved = orderRepository.save(order);



        return  OrderResponse.from(saved);
    }

    public List<OrderResponse> getOrdersByMember(Long memberId){
            List<Orders> orders = orderRepository.findBymemberId(memberId);


        return orders.stream().map(OrderResponse::from).toList();
    }

    @Transactional
    public OrderResponse cancel(Long id) {
        Orders order =  orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("주문을 찾을 수 없습니다."));
        if(order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }
        Product product = order.getProduct();
        product.setStock(product.getStock()+order.getQuantity());
        order.setStatus(OrderStatus.CANCELED);
        return OrderResponse.from(order);

    }

}
