package com.project.Enovel.domain.order.service;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.cart.service.CartService;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.entity.OrderItem;
import com.project.Enovel.domain.order.repository.OrderRepository;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import com.project.Enovel.global.exception.GlobalException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartService cartService;

    @Transactional
    public Order createOrder(Member buyer, List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("제품 ID 목록이 비어 있습니다.");
        }

        Order order = Order.builder().buyer(buyer).build();
        for (Long productId : productIds) {
            Product product = productService.getProduct(productId);
            if (product != null) {
                OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .order(order)
                        .build();
                order.addOrderItem(orderItem);
            }
        }
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.setPaymentDone(); // 결제 완료 처리
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.setCancelDone(); // 주문 취소 처리
        orderRepository.save(order);
    }

    @Transactional
    public void refundOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.setRefundDone(); // 환불 처리
        orderRepository.save(order);
    }
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 번호가 유효하지 않습니다."));
    }
    // 사용자의 주문 목록을 조회하는 메소드
    @Transactional(readOnly = true)
    public List<Order> findOrdersByMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("멤버 ID가 유효하지 않습니다.");
        }
        return orderRepository.findOrdersByBuyer_Id(memberId);
    }
    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean actorCanSee(Member actor, Order order) {
        if (actor == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
        }
        if (order == null) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다.");
        }
        return order.getBuyer().equals(actor);
    }

    public void checkCanPay(String orderCode, long pgPayPrice) {
        Order order = findByCode(orderCode).orElse(null);

        if (order == null)
            throw new GlobalException("400-1", "존재하지 않는 주문입니다.");

        checkCanPay(order, pgPayPrice);
    }

    public void checkCanPay(Order order, long pgPayPrice) {
        if (!canPay(order, pgPayPrice))
            throw new GlobalException("400-2", "PG결제금액 혹은 예치금이 부족하여 결제할 수 없습니다.");
    }

    public Optional<Order> findByCode(String code) {
        long id = Long.parseLong(code.split("__", 2)[1]);

        return findById(id);
    }

    public boolean canPay(Order order, long pgPayPrice) {
        if (!order.isPayable()) return false;

        return order.calcPayPrice() <= pgPayPrice;
    }

    @Transactional
    public void cancel(Order order) {
        if (!order.isCancelable())
            throw new GlobalException("400-1", "취소할 수 없는 주문입니다.");

        order.setCancelDone();

        if (order.isPayDone())
            refundOrder(order.getId());
    }

    public boolean canCancel(Member actor, Order order) {
        return actor.equals(order.getBuyer()) && order.isCancelable();
    }

    @Transactional
    public void payByTossPayments(Order order, long pgPayPrice) {
        long payPrice = order.calcPayPrice();
        // 예치금 사용 로직 삭제...
        payDone(order);
    }

    private void payDone(Order order) {
        order.setPaymentDone();

        order.getOrderItems()
                .stream()
                .forEach(orderItem -> {
                    Product product = orderItem.getProduct();
                });
    }
}
