package com.project.Enovel.domain.product.controller;

import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")

    @GetMapping("/create")
    public String createProduct() {
        this.productService.createProduct;
        return "redirect:/product/list";
    }
}
