package com.project.Enovel.domain.order.controller;

import ch.qos.logback.core.model.Model;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@AuthenticationPrincipal Member member, @RequestParam("productIds") String productIds) {
        List<Long> productIdList = Arrays.stream(productIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        orderService.createOrder(member, productIdList);
        return "redirect:/orders/my";
    }

    @PostMapping("/{orderId}/pay")
    public String payOrder(@PathVariable Long orderId) {
        orderService.payOrder(orderId);
        return "redirect:/orders/my";
    }

    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders/my";
    }

    @PostMapping("/{orderId}/refund")
    public String refundOrder(@PathVariable Long orderId) {
        orderService.refundOrder(orderId);
        return "redirect:/orders/my";
    }

    @GetMapping("/my")
    public String myOrders(@AuthenticationPrincipal Member member, Model model) {
        // 여기서는 회원이 자신의 주문만 볼 수 있도록 필터링하는 로직을 구현할 수 있습니다.
        // 예제 코드는 단순화를 위해 생략했습니다.
        return "orders/my_orders";
    }
}