package com.project.Enovel.domain.cart.repository;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByProductAndMember(Product product, Member member);

    Cart getCartItemByProductAndMember(Product product, Member member);

    Cart findByProduct(Product product);
}
