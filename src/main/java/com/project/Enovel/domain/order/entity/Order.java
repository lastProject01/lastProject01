package com.project.Enovel.domain.order.entity;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "order_")
public class Order extends BaseEntity {
    @ManyToOne
    private Member buyer;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;
    private LocalDateTime payDate;
    private LocalDateTime cancelDate;
    private LocalDateTime refundDate;

    // 주문 항목 추가
    public void addOrderItem(Product product) {
        OrderItem orderItem = OrderItem.builder()
                .order(this)
                .product(product)
                .build();
        this.orderItems.add(orderItem);
    }

    // 총 결제 금액 계산
    public long calculateTotalPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getPrice)
                .sum();
    }

    // 결제 완료 처리
    public void markPaid() {
        this.payDate = LocalDateTime.now();
        // 주문 상태 업데이트 로직 추가 가능
    }

    // 주문 취소 처리
    public void markCancelled() {
        this.cancelDate = LocalDateTime.now();
        // 주문 상태 업데이트 로직 추가 가능
    }

    // 환불 처리
    public void markRefunded() {
        this.refundDate = LocalDateTime.now();
        // 주문 상태 업데이트 로직 추가 가능
    }
}
