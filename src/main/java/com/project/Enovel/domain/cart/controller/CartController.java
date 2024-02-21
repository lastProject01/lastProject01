package com.project.Enovel.domain.cart.controller;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.cart.service.CartService;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/list/{id}")
    public String cartList(@PathVariable(value = "id")Long id, Model model) {
        Member member = this.memberService.getMemberFindById(id);
        List<Cart> cartList = member.getCartList();

        model.addAttribute("cartList", cartList);

        return "cart/cart_list";
    }

    @PostMapping("/add/{id}")
    public String addCItem(@PathVariable(value = "id")Long id, Principal principal) {
        Product product = this.productService.getProduct(id);

        Member member = this.memberService.getMember(principal.getName());

        this.cartService.addItem(product, member);

        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable(value = "id")Long id, Principal principal) {
        Product product = this.productService.getProduct(id);

        Member member = this.memberService.getMember(principal.getName());

        this.cartService.deleteItem(product, member);

        return String.format("redirect:/cart/list/%d", member.getId());
    }



}
