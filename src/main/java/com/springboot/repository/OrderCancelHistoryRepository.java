package com.springboot.repository;

import com.springboot.domain.OrderCancelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelHistoryRepository extends JpaRepository<OrderCancelHistory, Long> {
}
