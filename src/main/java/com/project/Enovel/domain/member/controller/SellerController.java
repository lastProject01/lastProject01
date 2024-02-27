package com.project.Enovel.domain.member.controller;


import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final ProductService productService;
    private final MemberService memberService;

    //상품 목록 출력
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
