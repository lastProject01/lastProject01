package com.project.Enovel;

import com.project.Enovel.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest
//@ActiveProfiles("test")
//class EnovelApplicationTests {
//
//	@Autowired
//	private ReviewRepository reviewRepository;
//
//	@Test
//	void contextLoads() {
//		// 데이터 로드 검증
//		assertThat(reviewRepository.count()).isGreaterThan(0);
//	}
//
//	@Test
//	void testReviewData() {
//		// 특정 리뷰 데이터 검증
//		var review = reviewRepository.findById(1L);
//		assertThat(review).isPresent();
//		assertThat(review.get().getContent()).isEqualTo("테스트 리뷰 내용");
//	}
//}
