package com.project.Enovel.domain.order.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // 올바른 Model 임포트
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 상세 정보 조회
    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "order/detail";
    }

    // 주문 생성
    @PostMapping("/create")
    public String createOrder(@AuthenticationPrincipal Member member, @RequestParam("productIds") String productIds) {
        List<Long> productIdList = Arrays.stream(productIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        orderService.createOrder(member, productIdList);
        return "redirect:/order/my";
    }

    // 주문 결제
    @PostMapping("/{orderId}/pay")
    public String payOrder(@PathVariable("orderId") Long orderId) {
        orderService.payOrder(orderId);
        return "redirect:/order/my";
    }

    // 주문 취소
    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order/my";
    }

    // 주문 환불
    @PostMapping("/{orderId}/refund")
    public String refundOrder(@PathVariable("orderId") Long orderId) {
        orderService.refundOrder(orderId);
        return "redirect:/order/my";
    }

//    // 사용자의 주문 목록 조회
//    @GetMapping("/my")
//    public String myOrders(@AuthenticationPrincipal Member member, Model model) {
//        List<Order> myOrders = orderService.findOrdersByMember(member);
//        model.addAttribute("orders", myOrders); // 모델 속성 이름 수정 'order'에서 'orders'로 변경
//        return "order/my_orders"; // 뷰 이름 수정 'my_order'에서 'my_orders'로 변경
//    }
}
