package com.project.Enovel.domain.member.controller;


import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final ProductService productService;
    private final SellerService sellerService;
    private final MemberService memberService;

    // 상품 목록 출력
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("products")
    public String productList(Model model, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());

        List<Product> productList;

        // 만약 멤버가 어드민인 경우 모든 상품 목록을 가져옴
        if (member.isCheckedAdmin()) {
            productList = this.productService.getList();
        } else {
            // 멤버가 셀러인 경우 해당 셀러의 상품 목록을 가져옴
            productList = this.sellerService.getSellerList(member);

            // 셀러가 아니면서 현재 사용자와 멤버의 이름이 일치하지 않는 경우 권한 없음 익셉션 발생
            if (!member.isCheckedSeller() && !member.getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
            }
        }

        model.addAttribute("productList", productList);

        return "seller/seller_list";
    }

    //상품 세부
    @GetMapping("/detail/{id}")
    public String detailProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = this.productService.getProduct(id);

        model.addAttribute("product", product);

        return "product/product_detail";
    }
}
