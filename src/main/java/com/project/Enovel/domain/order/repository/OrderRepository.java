package com.project.Enovel.domain.order.repository;

import com.project.Enovel.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}