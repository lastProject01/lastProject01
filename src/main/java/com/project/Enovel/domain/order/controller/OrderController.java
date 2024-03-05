package com.project.Enovel.domain.order.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.service.OrderService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // 올바른 Model 임포트
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final MemberService memberService;

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
    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        // 상품 목록을 가져와 모델에 추가
        List<Product> products = productService.getList();
        model.addAttribute("products", products);

        // 주문 생성 페이지 뷰 이름 반환
        return "order/create";
    }
    // 주문 생성
    @PostMapping("/create")
    public String createOrder(@RequestParam("productIds") String productIds) {
        // 현재 인증된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 현재 로그인한 사용자 이름 가져오기

        // 사용자 이름을 사용하여 Member 객체 조회
        Member buyer = memberService.getMember(currentUsername); // MemberService에서 제공하는 메서드 사용

        List<Long> productIdList = Arrays.stream(productIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Order order = orderService.createOrder(buyer, productIdList); // 조회된 Member 객체 사용
        return "redirect:/order/detail/" + order.getId();
    }

    @PostMapping("/createFromCart")
    public String createFromCart(Principal principal, RedirectAttributes redirectAttributes) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = principal.getName();
        Member member = memberService.getMember(username);

        if (member == null) {
            // 사용자 정보가 없는 경우, 적절한 오류 처리
            redirectAttributes.addFlashAttribute("errorMessage", "사용자 정보를 찾을 수 없습니다.");
            return "redirect:/errorPage";
        }

        try {
            // 카트에 담긴 항목으로부터 주문 생성
            Order order = orderService.createFromCart(member);

            // 주문이 성공적으로 생성된 경우, 주문 상세 페이지로 리다이렉트
            return "redirect:/order/detail/" + order.getId();
        } catch (IllegalArgumentException e) {
            // 장바구니가 비어있거나 다른 문제가 있는 경우, 적절한 오류 처리
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/errorPage";
        }
    }

    // 주문 결제
    @PostMapping("/{orderId}/pay")
    public String payOrder(@PathVariable("orderId") Long orderId) {
        orderService.payOrder(orderId);
        return "redirect:/order/detail/" + orderId;
    }

    // 주문 취소
    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order/detail/" + orderId;
    }

    // 주문 환불
    @PostMapping("/{orderId}/refund")
    public String refundOrder(@PathVariable("orderId") Long orderId) {
        orderService.refundOrder(orderId);
        return "redirect:/order/detail/" + orderId;  // 다시 시작
    }
    // 현재 사용자의 주문 목록을 보여주는 메소드
    @GetMapping("/myList")
    public String showMyOrders(Principal principal, Model model) {
        String username = principal.getName(); // 현재 로그인한 사용자의 사용자명을 얻습니다.
        Member member = memberService.getMember(username); // 사용자명을 바탕으로 Member 객체를 조회합니다.

        if (member == null) {
            // Member 객체가 null인 경우, 적절한 예외 처리나 오류 메시지를 반환합니다.
            return "redirect:/errorPage";
        }

        Long memberId = member.getId(); // Member 객체에서 ID를 얻습니다.
        List<Order> orders = orderService.findOrdersByMemberId(memberId); // 회원 ID를 사용하여 주문 목록을 조회합니다.
        model.addAttribute("orders", orders);
        return "/order/myList"; // 주문 목록을 포함한 뷰를 반환합니다.
    }
}