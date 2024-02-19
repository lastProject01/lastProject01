package com.project.Enovel.domain.review.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create")
    public String createReview(@RequestParam("content") String content, Principal principal) {
       Long orderItemId = 1L;
        String username = principal.getName(); // 현재 인증된 사용자의 이름을 가져옵니다.
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 사용자입니다.")); // 사용자가 없는 경우 UsernameNotFoundException를 던집니다.

        // 여기서는 사용자가 유효하다고 가정하고 리뷰 생성 로직을 계속합니다.
        reviewService.create(member.getId(), content, orderItemId);
        return "redirect:/review/detail"; // 여기서 리다이렉트 경로를 확인하세요. 적절한 뷰 이름이 필요합니다.
    }

    @GetMapping("/detail")
    public String reviewDetail(Model model) {
        // 리뷰 목록을 가져오는 로직
        var reviews = reviewService.findAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/detail"; // 'review/detail.html' 템플릿으로 경로 수정
    }

    @GetMapping("/createForm")
    public String createReviewForm() {
        return "review/createForm"; // 'review/createForm.html' 템플릿으로 경로 수정
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
