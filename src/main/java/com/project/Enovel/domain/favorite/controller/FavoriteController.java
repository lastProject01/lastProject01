package com.project.Enovel.domain.favorite.controller;

import com.project.Enovel.domain.favorite.entity.Favorite;
import com.project.Enovel.domain.favorite.service.FavoriteService;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
    private final MemberService memberService;
    private final ProductService productService;
    private final FavoriteService favoriteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String cartList(Model model, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        List<Favorite> favoriteList = this.favoriteService.getFavoriteList(member);

        if (!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        model.addAttribute("favoriteList", favoriteList);

        return "favorite/favorite_list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{id}")
    public String addItem(@PathVariable(value = "id") Long id, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Product product = this.productService.getProduct(id);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 상품 입니다.");
        }

        this.favoriteService.favorite(product, member);

        return "redirect:/product/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable(value = "id") Long id, Principal principal) {
        Favorite favorite = this.favoriteService.getCartItem(id);
        Member member = this.memberService.getMember(favorite.getMember().getUsername());

        if (!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        Product product = this.productService.getProduct(favorite.getProduct().getId());

        if(product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 상품 입니다.");
        }

        this.favoriteService.unFavorite(favorite);

        return "redirect:/favorite/list";
    }
}

