package com.springboot.repository;

import com.springboot.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
        List<Orders> findByMemberId(Long memberId);

}
