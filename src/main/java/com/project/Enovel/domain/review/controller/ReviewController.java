package com.project.Enovel.domain.review.controller;

import com.project.Enovel.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public String createReview(@RequestParam("content") String content) {

        Long memberId = 1L; // 예시 ID, 실제 구현에서는 사용자 세션 또는 인증 정보에서 ID를 가져와야 합니다.
        Long orderItemId = 1L; // 예시 ID, 실제 구현에서는 필요한 로직에 따라 ID를 설정해야 합니다.

        reviewService.create(memberId, orderItemId, content);
        return "redirect:/reviewDetail";
    }

    @GetMapping("/reviewDetail")
    public String reviewDetail(Model model) {
        // 리뷰 목록을 가져오는 로직
        var reviews = reviewService.findAllReviews(); // ReviewService에서 리뷰 목록을 가져오는 메소드를 가정
        model.addAttribute("reviews", reviews);
        return "reviewDetail"; // templates 디렉토리 아래에 있는 reviewDetail.html을 가리킴
    }

}
