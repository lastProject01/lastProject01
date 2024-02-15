package com.project.Enovel.domain.product.controller;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.form.ProductCreateForm;
import com.project.Enovel.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public String productList(Model model) {
        List<Product> product = this.productService.getList();
        model.addAttribute("product", product);

        return"product_list";
    }

    @GetMapping("/create")
    public String createProduct(ProductCreateForm productCreateForm) {
       return"product_create";
    }

    @PostMapping("/create")
    public String createProduct(@Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()) {
            return "redirect:/product/create";
        }

        this.productService.createProduct(productCreateForm.getProductName(),
                productCreateForm.getCategory(),
                productCreateForm.getPrice(),
                productCreateForm.getProductImg(),
                productCreateForm.getIntroduceImg());

        return "redirect:/product/list";
    }

    @GetMapping("/detail/{id}")
    public String detailProduct(@PathVariable(value = "id")Long id, Model model) {
        Product product = this.productService.getProduct(id);

        model.addAttribute("product", product);

        return "product_detail";
    }

    @GetMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id")Long id, ProductCreateForm productCreateForm) {
        Product product = this.productService.getProduct(id);
        return "product_modify";
    }

    @PostMapping("/modify")
    public String modifyProduct(@PathVariable(value = "id")Long id, @Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {
        Product product = this.productService.getProduct(id);

        if (bindingResult.hasErrors()) {
            return"redirect:/product/modify";
        }

        this.productService.modifyProduct(product,
                productCreateForm.getProductName(),
                productCreateForm.getCategory(),
                productCreateForm.getPrice(),
                productCreateForm.getProductImg(),
                productCreateForm.getIntroduceImg());

        return "redirect:/product/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct() {
        return"";
    }




}
