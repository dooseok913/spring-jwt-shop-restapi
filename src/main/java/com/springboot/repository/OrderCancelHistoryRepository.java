package com.springboot.repository;

import com.springboot.domain.OrderCancelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderCancelHistoryRepository extends JpaRepository<OrderCancelHistory, Long> {

    List<OrderCancelHistory> findByCanceledBy(String username);

}
