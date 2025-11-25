package com.springboot.service;

import com.springboot.domain.CustomUserDetails;
import com.springboot.domain.Member;
import com.springboot.exception.CustomException;
import com.springboot.exception.ErrorCode;
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
                .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return  new CustomUserDetails(member);
    }
}
