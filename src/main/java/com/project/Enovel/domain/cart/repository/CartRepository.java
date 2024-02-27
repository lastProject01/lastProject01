package com.project.Enovel.domain.cart.repository;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByProduct(Product product);
    List<Cart> findByBuyer(Member buyer);
}
