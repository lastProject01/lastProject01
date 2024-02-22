package com.project.Enovel.domain.cart.service;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.cart.repository.CartRepository;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void addItem(Product product, Member member) {

        Cart cart = Cart.builder()
                .product(product)
                .member(member)
                .build();

        this.cartRepository.save(cart);
    }

    @Transactional
    public void deleteItem(Product product, Member member) {
        this.cartRepository.deleteByProductAndMember(product, member);
    }


    public Optional<Cart> getCartItem(Long id) {
        return this.cartRepository.findById(id);
    }
}
