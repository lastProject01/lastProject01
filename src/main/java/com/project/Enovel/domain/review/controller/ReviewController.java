package com.project.Enovel.domain.review.controller;

import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/create")
    public String createReview(@RequestParam("memberId") Long memberId, @RequestParam("orderItemId") Long orderItemId, @RequestParam("content") String content) {

        reviewService.create(memberId, orderItemId, content);
        return "redirect:/reviewDetail";
    }
}
