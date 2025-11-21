package com.springboot.controller;

import com.springboot.dto.MemberLoginRequest;
import com.springboot.dto.MemberRequest;
import com.springboot.dto.MemberResponse;
import com.springboot.repository.MemberRepository;
import com.springboot.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private  final MemberService memberService;
    @PostMapping
    public String create(@RequestBody@Valid MemberRequest request){

        memberService.create(request);
        return "회원 가입 완료";
    }

    @GetMapping
    public List<MemberResponse> getAll(){
        return memberService.getAll();
    }


    @PostMapping("/login")
    public String login(@RequestBody MemberLoginRequest request) {
        return  memberService.login(request);
    }



}
