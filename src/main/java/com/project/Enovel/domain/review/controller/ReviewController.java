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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository; // MemberRepository 주입
    @PostMapping("/create")
    public String createReview(@RequestParam("content") String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 인증된 사용자의 username 가져오기

        // username을 사용하여 Member 엔티티 조회
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        // Review 생성 시 Member 엔티티를 연관관계로 설정
        reviewService.create(member.getId(), content); // 수정된 서비스 메소드 사용
        return "redirect:/review/detail";
    }
}
