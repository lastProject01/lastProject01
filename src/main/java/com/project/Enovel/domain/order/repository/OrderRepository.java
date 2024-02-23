package com.project.Enovel.domain.order.repository;

import com.project.Enovel.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 사용자 ID로 주문을 조회하는 메소드
    List<Order> findOrdersByBuyer_Id(Long memberId);
}