package com.project.Enovel.domain;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    //상품 목록 메인 페이지에서 바로 출력
    @GetMapping("/")
    public String main(Model model) {
        List<Product> productList = this.productService.getList();
        model.addAttribute("productList", productList);

        // 메서드 로직
        return "main/main";
    }
}
