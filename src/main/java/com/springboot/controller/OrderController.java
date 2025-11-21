package com.springboot.controller;

import com.springboot.dto.OrderRequest;
import com.springboot.dto.OrderResponse;
import com.springboot.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public OrderResponse create(@RequestBody @Valid OrderRequest request,
                                HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        return  orderService.create(username, request);
    }

    @GetMapping("/{id}")
    public List<OrderResponse> getByMember(@PathVariable Long memberId){
        return orderService.getOrdersByMember(memberId);
    }

    @DeleteMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id){
        return  orderService.cancel(id);
    }


}
