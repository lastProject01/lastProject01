package com.project.Enovel.domain.favorite.repository;

import com.project.Enovel.domain.favorite.entity.Favorite;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByProduct(Product product, Member member);
    List<Favorite> findByMember(Member member);
}

