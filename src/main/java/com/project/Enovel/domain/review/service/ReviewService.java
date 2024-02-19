package com.project.Enovel.domain.review.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Review create(Long memberId, String content, Long orderItemId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));

        Review newReview = Review.builder()
                .member(member)
                .content(content)
                .orderItemId(orderItemId)
                .build();
        reviewRepository.save(newReview);
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

    public List<Review> findAllReviews() {
        return reviewRepository.findAll(); // 모든 리뷰를 조회하여 반환
    }
}