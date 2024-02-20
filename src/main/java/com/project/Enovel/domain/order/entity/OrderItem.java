package com.project.Enovel.domain.order.entity;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem extends BaseEntity {
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    // 실제 결제 금액 반환
    public long getPrice() {
        return product.getPrice(); // 상품 가격을 반환
    }
}