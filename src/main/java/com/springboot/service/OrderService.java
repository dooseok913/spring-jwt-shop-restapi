package com.springboot.service;


import com.springboot.domain.Member;
import com.springboot.domain.OrderStatus;
import com.springboot.domain.Orders;
import com.springboot.domain.Product;
import com.springboot.dto.OrderCancelRequest;
import com.springboot.dto.OrderCancelResponse;
import com.springboot.dto.OrderRequest;
import com.springboot.dto.OrderResponse;
import com.springboot.exception.CustomException;
import com.springboot.exception.ErrorCode;
import com.springboot.repository.MemberRepository;
import com.springboot.repository.OrderCancelHistoryRepository;
import com.springboot.repository.OrderRepository;
import com.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final OrderCancelHistoryRepository cancelHistoryRepository;



    @Transactional
    public OrderResponse create(String username, OrderRequest request){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));



        product.reduceStock(request.quantity());

        Orders order = new Orders(member, product, request.quantity());
        Orders saved = orderRepository.save(order);



        return  OrderResponse.from(saved);
    }

    public List<OrderResponse> getOrdersByMember(String username){
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
            List<Orders> orders = orderRepository.findByMemberId(member.getId());


        return orders.stream().map(OrderResponse::from).toList();
    }

    @Transactional
    public OrderCancelResponse cancel(Long orderId, String username, OrderCancelRequest request) {
        Orders order =  orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if(!order.getMember().getUsername().equals(username)){
            throw new CustomException(ErrorCode.ORDER_UNAUTHORIZED);
        }


        if(order.getOrderAt().isBefore(LocalDateTime.now().minusMinutes(30))){
            throw new CustomException(ErrorCode.ORDER_CANNOT_CANCEL);
        }
        order.validateCancelable();
        order.cancel();


        order.getProduct().addStock(order.getQuantity());




        return OrderCancelResponse.from(order);

    }

}
