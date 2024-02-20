package com.project.Enovel.domain.order.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.order.entity.OrderItem;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.repository.OrderRepository;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public Order createOrder(Member buyer, List<Long> productIds) {
        // Order 인스턴스 생성
        Order order = Order.builder()
                .buyer(buyer)
                .build();

        // 제품 ID 목록을 반복하여 각 제품에 대한 OrderItem을 추가
        productIds.forEach(productId -> {
            Product product = productService.getProduct(productId);
            order.addOrderItem(product);
        });

        // 주문 저장 및 반환
        return orderRepository.save(order);
    }

    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.markPaid();
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.markCancelled();
        orderRepository.save(order);
    }

    @Transactional
    public void refundOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.markRefunded();
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
    }
}
