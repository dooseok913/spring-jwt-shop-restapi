package com.springboot.controller;

import com.springboot.domain.CustomUserDetails;
import com.springboot.dto.*;
import com.springboot.service.OrderService;
import com.springboot.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name="주문 API", description = "주문 생성*취소*조회")
public class OrderController {

    private final OrderService orderService;
    private  final JwtUtil jwtUtil;

    @Operation(summary = "주문 생성")
    @PostMapping
    public ApiResponse<OrderResponse> create(@RequestBody @Valid OrderRequest request,
                                @AuthenticationPrincipal CustomUserDetails user) {

        return  ApiResponse.ok(orderService.create(user.getUsername(), request));
    }

    @Operation(summary = "내 주문 목록 조회")
    @GetMapping
    public ApiResponse<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal CustomUserDetails user){
        return ApiResponse.ok(orderService.getOrdersByMember(user.getUsername()));
    }

    @Operation(summary = "주문 취소")
    @PostMapping("/{id}/cancel")
    public ApiResponse<OrderCancelResponse> cancel(@PathVariable Long id,
                                      @RequestBody OrderCancelRequest request,
                                      @AuthenticationPrincipal CustomUserDetails user
                                      ){

        return  ApiResponse.ok(orderService.cancel(id, user.getUsername(), request));
    }


    // OrderCancelHistory 조회 API 만들기
    @Operation(summary = "내 주문 취소 히스토리 조회")
    @GetMapping("/cancel/history")

    public ApiResponse<List<OrderCancelHistoryResponse>> getMyCancelHistory(@AuthenticationPrincipal CustomUserDetails user) {

        return ApiResponse.ok(orderService.getMyCancelHistory(user.getUsername()));
    }

}
