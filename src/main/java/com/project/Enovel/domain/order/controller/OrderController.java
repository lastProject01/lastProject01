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

    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable("orderId") Long orderId, Model model) {
        try {
            Order order = orderService.getOrderById(orderId);
            model.addAttribute("order", order);
            return "order/detail";
        } catch (IllegalArgumentException e) {
            // 적절한 예외 처리 (예: 사용자를 오류 페이지로 리다이렉트)
            return "redirect:/error-page";  // 오류 페이지나 홈페이지로 리다이렉트
        }
    }

    // 주문 생성
    @PostMapping("/create")
    public String createOrder(@AuthenticationPrincipal Member member, @RequestParam("productIds") String productIds) {
        List<Long> productIdList = Arrays.stream(productIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Order order = orderService.createOrder(member, productIdList);
        return "redirect:/order/detail/" + order.getId();  // 변경된 부분
    }

    // 주문 결제
    @PostMapping("/{orderId}/pay")
    public String payOrder(@PathVariable("orderId") Long orderId) {
        orderService.payOrder(orderId);
        return "redirect:/order/detail/" + orderId;  // 변경된 부분
    }

    // 주문 취소
    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order/detail/" + orderId;  // 변경된 부분
    }

    // 주문 환불
    @PostMapping("/{orderId}/refund")
    public String refundOrder(@PathVariable("orderId") Long orderId) {
        orderService.refundOrder(orderId);
        return "redirect:/order/detail/" + orderId;  // 다시 시작
    }
}