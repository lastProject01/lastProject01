package com.project.Enovel.domain.member.controller;


import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
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

import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final ProductService productService;
    private final MemberService memberService;

    //상품 목록 출력
//    public String productList(Model model, Authentication authentication) {
//        List<Product> productList;
//
//        // 현재 로그인한 사용자의 권한을 가져옵니다.
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
//            // 관리자일 경우 모든 상품 리스트를 가져옵니다.
//            productList = productService.getList();
//        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_SELLER"))) {
//            // 판매자일 경우 현재 판매자의 상품 리스트만 가져옵니다.
//            // 판매자 정보를 기반으로 상품을 가져오는 메서드가 필요합니다. productService.getSellerProductList() 등으로 가정합니다.
//            productList = productService.getSellerProductList(authentication.getName());
//        } else {
//            // 다른 권한이 있는 경우에는 접근을 거부하거나 적절한 처리를 수행합니다.
//            throw new AccessDeniedException("Access denied");
//        }
//
//        model.addAttribute("productList", productList);
//
//        return "seller/seller_list";
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("products")
    public String productList(Model model) {
        List<Product> productList = this.productService.getList();
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
