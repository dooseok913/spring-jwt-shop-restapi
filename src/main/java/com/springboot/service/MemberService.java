package com.springboot.service;

import com.springboot.domain.Member;
import com.springboot.dto.MemberLoginRequest;
import com.springboot.dto.MemberRequest;
import com.springboot.dto.MemberResponse;
import com.springboot.exception.CustomException;
import com.springboot.exception.ErrorCode;
import com.springboot.repository.MemberRepository;
import com.springboot.util.JwtUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private  final MemberRepository memberRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void create(MemberRequest request){
        if(memberRepository.existsByUsername(request.username())) {
            throw new CustomException(ErrorCode.DUPLICATE_MEMBER);
        }
        String encodePw = passwordEncoder.encode(request.password());

        Member member = new Member(request.username(), encodePw, request.name(), request.email());
        Member saved = memberRepository.save(member);

    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberResponse::from).toList();
    }

    public String login (MemberLoginRequest request){

        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));


        if( !passwordEncoder.matches(request.password(),member.getPassword()) ) {
            throw  new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtUtil.createToken(member.getUsername());

    }




}
