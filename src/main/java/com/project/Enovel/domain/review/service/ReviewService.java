package com.project.Enovel.domain.review.service;

import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review create(Long memberId, Long orderItemId, String content) {
        Review newReview = Review.builder()
                .content(content)
                .orderItemId(orderItemId) // 이 부분은 Long 타입의 ID 값을 직접 할당
                .memberId(memberId) // 이 부분도 Long 타입의 ID 값을 직접 할당
                .build();
        this.reviewRepository.save(newReview);
        return newReview;
    }

    public Review getReview(Long id) {
        return this.reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("리뷰를 찾을 수 없습니다."));
    }

    @Transactional
    public void modify(Review review, String content) {
        review.setContent(content);
        this.reviewRepository.save(review);
    }

    @Transactional
    public void delete(Review review) {
        this.reviewRepository.delete(review);
    }
}