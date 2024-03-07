package com.project.Enovel.domain;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    //상품 목록 메인 페이지에서 바로 출력
    @GetMapping("/")
    public String main(Model model,
                       @RequestParam(value = "category", defaultValue = "", required = false) String category) {
        List<Product> productList = this.productService.getAllProducts();

        if (!category.isEmpty()) {
            productList = productService.getList(category);
        } else {
            productList = productService.getAllProducts();
        }


        model.addAttribute("category", category);
        model.addAttribute("productList", productList);

        // 메서드 로직
        return "main/main";
    }

    @GetMapping("/main")
    public String main2(Model model,
                       @RequestParam(value = "category", defaultValue = "", required = false) String category) {
        List<Product> productList = this.productService.getAllProducts();

        if (!category.isEmpty()) {
            productList = productService.getList(category);
        } else {
            productList = productService.getAllProducts();
        }


        model.addAttribute("category", category);
        model.addAttribute("productList", productList);

        // 메서드 로직
        return "main/main";
    }
}
