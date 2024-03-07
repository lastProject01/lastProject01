package com.project.Enovel.domain.review.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.order.service.OrderService;
import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final MemberRepository memberRepository; // MemberRepository 주입
    private final MemberService memberService;

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public String createReview(@RequestParam("productId") Long productId, @RequestParam("content") String content, Principal principal) {
        String username = principal.getName(); // 현재 인증된 사용자의 이름을 가져옵니다.
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 사용자입니다.")); // 사용자가 없는 경우 UsernameNotFoundException를 던집니다.

        // 주문한 상품인지 확인
        boolean ordered = orderService.checkOrderedByMember(productId, member.getId());
        if (!ordered) {
            // 사용자가 해당 상품을 주문하지 않았다면 리뷰 작성을 거부
            return "redirect:/error"; // 오류 페이지나 적절한 경로로 리다이렉트
        }

        // 사용자가 주문한 상품이면 리뷰를 추가
        reviewService.create(member.getId(), content, productId);
        return "redirect:/review/detail"; // 리뷰 목록 페이지로 리다이렉트
    }

    @GetMapping("/detail")
    public String reviewDetail(Model model) {
        // 리뷰 목록을 가져오는 로직
        var reviews = reviewService.findAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/detail"; // 'review/detail.html' 템플릿으로 경로 수정
    }

    @GetMapping("/createForm/{orderId}")
    public String createReviewForm(@PathVariable("orderId") Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "review/createForm";
    }

    @GetMapping("/modify/{id}")
    public String modifyReview(@PathVariable("id") Long id, Model model) {
        Review review = reviewService.getReview(id);
        model.addAttribute("review", review);
        return "review/modifyForm"; // 리뷰 수정 폼 뷰
    }

    @PostMapping("/modify/{id}")
    public String modifyReview(@PathVariable("id") Long id, @RequestParam("content") String content, RedirectAttributes redirectAttributes) {
        Review review = reviewService.getReview(id);
        reviewService.modify(review, content);
        return "redirect:/review/detail";
    }


    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        Review review = reviewService.getReview(id);
        reviewService.delete(review);
        return "redirect:/review/detail";
    }
}
