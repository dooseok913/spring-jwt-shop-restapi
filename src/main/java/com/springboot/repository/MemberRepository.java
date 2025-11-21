package com.springboot.repository;

import com.springboot.domain.Member;
import com.springboot.dto.MemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByUsername(String username);


    Optional<Member> findByUsername(String username);
}
