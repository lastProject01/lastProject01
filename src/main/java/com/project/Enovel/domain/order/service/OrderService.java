package com.project.Enovel.domain.order.service;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.cart.service.CartService;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.entity.OrderItem;
import com.project.Enovel.domain.order.repository.OrderRepository;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartService cartService;

    @Transactional
    public Order createOrder(Member buyer, List<Long> productIds) {
        // Order 인스턴스 생성
        Order order = Order.builder()
                .buyer(buyer)
                .build();

        // 제품 ID 목록을 반복하여 각 제품에 대한 OrderItem을 추가
        productIds.forEach(productId -> {
            Product product = productService.getProduct(productId);
            if (product != null) { // 제품이 존재하는지만 확인
                OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .order(order)
                        .build();
                order.addOrderItem(orderItem); // 수정됨: 제품 대신 주문 항목을 추가
            }
        });

        if (order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("주문할 수 있는 제품이 없습니다.");
        }

        return orderRepository.save(order);
    }

    @Transactional
    public Order createFromCart(Member buyer) {
        // 구매자의 장바구니 항목 가져오기
        List<Cart> cartItems = cartService.getCartList(buyer);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        // 장바구니 항목으로부터 제품 ID 목록 추출
        List<Long> productIds = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());

        // 제품 ID 목록을 사용하여 주문 생성
        Order order = createOrder(buyer, productIds);

        // 주문 생성 후 장바구니 비우기
        cartItems.forEach(cartService::deleteItem);

        return order;
    }
    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 번호가 유효하지 않습니다."));
        order.markPaid();
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 번호가 유효하지 않습니다."));
        order.markCancelled();
        orderRepository.save(order);
    }

    @Transactional
    public void refundOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 번호가 유효하지 않습니다."));
        order.markRefunded();
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 번호가 유효하지 않습니다."));
    }
    // 사용자의 주문 목록을 조회하는 메소드
    @Transactional(readOnly = true)
    public List<Order> findOrdersByMemberId(Long memberId) {
        return orderRepository.findOrdersByBuyer_Id(memberId);
    }
}
