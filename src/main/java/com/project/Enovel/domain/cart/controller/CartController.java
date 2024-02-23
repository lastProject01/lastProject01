package com.project.Enovel.domain.cart.controller;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.cart.service.CartService;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    //cart list 출력
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list/{id}")
    public String cartList(@PathVariable(value = "id")Long id, Model model, Principal principal) {
        Member member = this.memberService.getMemberFindById(id);
        List<Cart> cartList = member.getCartList();

        //회원 검증 코드
        if(!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        model.addAttribute("cartList", cartList);

        return "cart/cart_list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{id}")
    public String addItem(@PathVariable(value = "id")Long id, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());

        Product product = this.productService.getProduct(id);


        if(product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 상품 입니다.");
//            data not found exception
        }

        this.cartService.addItem(product, member);

        return "redirect:/product/list";
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable(value = "id")Long id, Principal principal) {
        Cart cart = this.cartService.getCartItem(id);
        Member member = this.memberService.getMember(cart.getMember().getUsername());

        //회원 검증 코드
        if(!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        Product product = this.productService.getProduct(cart.getProduct().getId());

        this.cartService.deleteItem(product, member);

        return String.format("redirect:/cart/list/%d", member.getId());
    }


}
