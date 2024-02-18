package com.project.Enovel.domain.product.controller;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.form.ProductCreateForm;
import com.project.Enovel.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public String productList(Model model,  @RequestParam(value="page", defaultValue="0") int page) {
        Page<Product> paging = this.productService.getList(page);
        model.addAttribute("paging", paging);

        return "product/product_list";
    }

    @GetMapping("/create")
    public String createProduct(ProductCreateForm productCreateForm) {
        return "product/product_create";
    }

    @PostMapping("/create")
    public String createProduct(ProductCreateForm productCreateForm, Principal principal) {


        this.productService.createProduct(productCreateForm.getProductName(),
                productCreateForm.getPrice(),
                productCreateForm.getProductImg(),
                productCreateForm.getContent());

        return "redirect:/product/list";
    }

    @GetMapping("/detail/{id}")
    public String detailProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = this.productService.getProduct(id);

        model.addAttribute("product", product);

        return "product/product_detail";
    }

    @GetMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, ProductCreateForm productCreateForm) {
        Product product = this.productService.getProduct(id);
        return "product/product_modify";
    }

    @PostMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, @Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {
        Product product = this.productService.getProduct(id);

        if (bindingResult.hasErrors()) {
            return "product/product_modify";
        }

        this.productService.modifyProduct(product,
                productCreateForm.getProductName(),
                productCreateForm.getPrice(),
                productCreateForm.getProductImg(),
                productCreateForm.getContent());

        return String.format("redirect:/product/detail/%d", product.getId());
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        Product product = this.productService.getProduct(id);

        this.productService.deleteProduct(product);

        return String.format("redirect:/product/detail/%d", product.getId());
    }


}
