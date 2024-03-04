package com.project.Enovel.domain.favorite.service;

import com.project.Enovel.domain.favorite.entity.Favorite;
import com.project.Enovel.domain.favorite.repository.FavoriteRepository;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public void favorite(Product product, Member member) {
        Favorite likeProduct = this.favoriteRepository.findByProduct(product, member);

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

