package com.springboot.service;

import com.springboot.domain.CustomUserDetails;
import com.springboot.domain.Member;
import com.springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername (String username){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("존재하지 않는 사용자"));
        return  new CustomUserDetails(member);
    }
}
