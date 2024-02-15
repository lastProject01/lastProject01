package com.project.Enovel.domain.review.repository;

import com.project.Enovel.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository <Review, Long>{
}
