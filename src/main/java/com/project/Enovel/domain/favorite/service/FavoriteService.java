package com.project.Enovel.domain.favorite.service;

import com.project.Enovel.domain.favorite.entity.Favorite;
import com.project.Enovel.domain.favorite.repository.FavoriteRepository;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public void favorite(Product product, Member member) {
        Favorite likeProduct = this.favoriteRepository.findByProductAndMember(product, member);

        if (likeProduct != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 찜한 상품 입니다.");
        }

        Favorite favorite = Favorite.builder()
                .product(product)
                .member(member)
                .build();

        this.favoriteRepository.save(favorite);
    }

    public void unFavorite(Favorite favorite) {
        this.favoriteRepository.delete(favorite);
    }

    public Favorite getCartItem(Long id) {
        Optional<Favorite> favorite = this.favoriteRepository.findById(id);
        return favorite.get();
    }

    public List<Favorite> getFavoriteList(Member member) {
        return this.favoriteRepository.findByMember(member);
    }

}

