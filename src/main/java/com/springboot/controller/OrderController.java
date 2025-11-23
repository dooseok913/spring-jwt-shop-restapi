package com.springboot.controller;

import com.springboot.domain.CustomUserDetails;
import com.springboot.dto.OrderCancelRequest;
import com.springboot.dto.OrderCancelResponse;
import com.springboot.dto.OrderRequest;
import com.springboot.dto.OrderResponse;
import com.springboot.service.OrderService;
import com.springboot.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private  final JwtUtil jwtUtil;

    @PostMapping
    public OrderResponse create(@RequestBody @Valid OrderRequest request,
                                @AuthenticationPrincipal CustomUserDetails user) {

        return  orderService.create(user.getUsername(), request);
    }

    @GetMapping
    public List<OrderResponse> getMyOrders(@AuthenticationPrincipal CustomUserDetails user){
        return orderService.getOrdersByMember(user.getUsername());
    }

    @PostMapping("/{id}/cancel")
    public OrderCancelResponse cancel(@PathVariable Long id,
                                      @RequestBody OrderCancelRequest request,
                                      @AuthenticationPrincipal CustomUserDetails user
                                      ){

        return  orderService.cancel(id, user.getUsername(), request);
    }


}
