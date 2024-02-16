package com.project.Enovel.domain.review.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    @PostMapping("/create")
    public String createReview(@RequestParam("content") String content, Model model) {
//        Member member = this.memberService.getMember(username);
        Long orderItemId = 1L; // 예시 ID

//        reviewService.create(memberId, orderItemId, content);

        // 리뷰 생성 후 바로 리뷰 목록을 가져와 모델에 추가
        var reviews = reviewService.findAllReviews();
        model.addAttribute("reviews", reviews);
        return "review/detail"; // 'review/detail.html' 템플릿으로 경로 수정
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
