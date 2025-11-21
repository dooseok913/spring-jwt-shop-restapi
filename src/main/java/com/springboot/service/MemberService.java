package com.springboot.service;

import com.springboot.domain.Member;
import com.springboot.dto.MemberLoginRequest;
import com.springboot.dto.MemberRequest;
import com.springboot.dto.MemberResponse;
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
            throw new RuntimeException("이미 존재하는 회원 입니다.");
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
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));


        if( !passwordEncoder.matches(request.password(),member.getPassword()) ) {
            throw  new RuntimeException("비밀번호가 일치하지 않습니다. ");
        }

        return jwtUtil.createToken(member.getUsername());

    }




}
