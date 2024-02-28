package com.project.Enovel.domain.cart.service;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.cart.repository.CartRepository;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void addItem(Product product, Member buyer) {
        Cart existingCart = this.cartRepository.findByProductAndBuyer(product, buyer);

        if (existingCart != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재 하는 상품 입니다.");
        }

        Cart cart = Cart.builder()
                .product(product)
                .buyer(buyer)
                .build();


        this.cartRepository.save(cart);
    }


    public void deleteItem(Cart cart) {
        this.cartRepository.delete(cart);
    }


    public Cart getCartItem(Long id) {
        //Optional<>은 데이터를 cart.get()으로 데이터를 받아 와야 한다.
        Optional<Cart> cart = this.cartRepository.findById(id);
        return cart.get();
    }

    public List<Cart> getCartList(Member buyer) {
        return this.cartRepository.findByBuyer(buyer);
    }
}
