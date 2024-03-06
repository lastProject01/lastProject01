package com.project.Enovel.domain.order.entity;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.global.base.BaseEntity;
import com.project.Enovel.global.config.AppConfig;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private LocalDateTime payDate;

    private LocalDateTime cancelDate;

    private LocalDateTime refundDate;

    // 주문 항목 추가
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 총 결제 금액 계산
    public long calculateTotalPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getPrice)
                .sum();
    }
    public void setPaymentDone() {
        payDate = LocalDateTime.now();
    }

    public void setCancelDone() {
        cancelDate = LocalDateTime.now();
    }
    public void setRefundDone() {
        refundDate = LocalDateTime.now();
    }
    public String getName() {
        if (orderItems.isEmpty()) {
            return "제품 이름 없음"; // 또는 다른 적절한 기본값
        }

        String name = orderItems.get(0).getProduct().getProductName();

        if (orderItems.size() > 1) {
            name += " 외 " + (orderItems.size() - 1) + "건";
        }

        return name;
    }

    public String getCode() {
        // yyyy-MM-dd 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDateTime 객체를 문자열로 변환
        return getCreateDate().format(formatter) + (AppConfig.isNotProd() ? "-test-" + UUID.randomUUID().toString() : "") + "__" + getId();
    }

    public boolean isPayable() {
        if (payDate != null) return false;
        if (cancelDate != null) return false;

        return true;
    }

    public String getForPrintPayStatus() {
        if (payDate != null)
            return "결제완료(" + payDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (cancelDate != null) return "-";

        return "결제대기";
    }

    public String getForPrintCancelStatus() {
        if (cancelDate != null)
            return "취소완료(" + cancelDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (!isCancelable()) return "취소불가능";

        return "취소가능";
    }

    public String getForPrintRefundStatus() {
        if (refundDate != null)
            return "환불완료(" + refundDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (payDate == null) return "-";
        if (!isCancelable()) return "-";

        return "환불가능";
    }

    public boolean isPayDone() {
        return payDate != null;
    }

    public boolean isCancelable() {
        if (cancelDate != null) return false;

        // 결제일자로부터 1시간 지나면 취소 불가능
        if (payDate != null && payDate.plusSeconds(AppConfig.getOrderCancelableSeconds()).isBefore(LocalDateTime.now())) return false;

        return true;
    }
    public long calcPayPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getPayPrice)
                .sum();
    }
}
