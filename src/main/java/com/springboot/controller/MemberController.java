
package com.springboot.controller;

import com.springboot.dto.ApiResponse;
import com.springboot.dto.MemberLoginRequest;
import com.springboot.dto.MemberRequest;
import com.springboot.dto.MemberResponse;
import com.springboot.repository.MemberRepository;
import com.springboot.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원 가입 * 로그인 * 조회")
public class MemberController {

    private  final MemberService memberService;

    @Operation(summary = "회원가입", description = "신규 회원을 생성합니다.")
    @PostMapping
    public ApiResponse<String> create(@RequestBody@Valid MemberRequest request){

        memberService.create(request);
        return ApiResponse.ok("회원 가입 완료");
    }

    @Operation(summary = "회원 전체 조회")
    @GetMapping
    public ApiResponse<List<MemberResponse>> getAll() {
        return ApiResponse.ok(memberService.getAll());
    }


    @Operation(summary = "로그인", description = "로그인 성공시 jwt 토큰을 반환합니다.")
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody MemberLoginRequest request) {

        return  ApiResponse.ok(memberService.login(request));
    }



}
