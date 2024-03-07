package com.project.Enovel.domain.review.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.order.entity.Order;
import com.project.Enovel.domain.order.entity.OrderItem;
import com.project.Enovel.domain.order.repository.OrderRepository;
import com.project.Enovel.domain.order.service.OrderService;
import com.project.Enovel.domain.review.entity.Review;
import com.project.Enovel.domain.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    @Autowired
    private OrderService orderService;

    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }
    @Transactional
    public Review create(Long memberId, String content, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        // 여기에 주문 확인 로직을 추가
        boolean ordered = checkOrderedByMember(productId, memberId);
        if (!ordered) {
            throw new IllegalStateException("이 상품에 대한 주문이 확인되지 않았습니다.");
        }
        Review newReview = new Review();
        newReview.setMember(member);
        newReview.setContent(content);
        newReview.setOrderItemId(productId); // 주문 항목 ID는 실제 구현에 맞게 조정 필요
        return reviewRepository.save(newReview);
    }

    @Transactional(readOnly = true)
    public boolean checkOrderedByMember(Long productId, Long memberId) {
        List<Order> orders = orderService.findOrdersByMemberId(memberId); // 수정된 부분
        for (Order order : orders) {
            for (OrderItem item : order.getOrderItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    return true;
                }
            }
        }
        return false;
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