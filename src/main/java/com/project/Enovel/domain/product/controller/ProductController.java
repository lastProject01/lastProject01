package com.project.Enovel.domain.product.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.form.ProductCreateForm;
import com.project.Enovel.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String productList(Model model) {
        List<Product> product = this.productService.getList();
        model.addAttribute("product", product);

        return "product/product_list";
    }


    //admin 등급과 seller 등급만 접근 가능
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createProduct(ProductCreateForm productCreateForm, Principal principal) {

        Member member = this.memberService.getMemberFindByUsername(principal.getName());

        //회원 등급 검증
        if (!member.isCheckedAdmin() || !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        return "product/product_create";
    }

    //admin 등급과 seller 등급만 접근 가능
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @PostMapping("/create")
    public String createProductPost(@Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {

        Member member = this.memberService.getMemberFindByUsername(principal.getName());

        //회원 등급 검증
        if (!member.isCheckedAdmin() || !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "redirect:/product/list";
        }


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

    //admin 등급과 seller 등급만 접근 가능
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, ProductCreateForm productCreateForm, Principal principal) {
        Member member = this.memberService.getMemberFindByUsername(principal.getName());

        //회원 등급 검증
        if (!member.isCheckedAdmin() || !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        Product product = this.productService.getProduct(id);
        return "product/product_modify";
    }

    //admin 등급과 seller 등급만 접근 가능
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @PostMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, @Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {

        Member member = this.memberService.getMemberFindByUsername(principal.getName());

        //회원 등급 검증
        if (!member.isCheckedAdmin() || !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

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

    //admin 등급과 seller 등급만 접근 가능
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id, Principal principal) {

        Member member = this.memberService.getMemberFindByUsername(principal.getName());

        //회원 등급 검증
        if (!member.isCheckedAdmin() || !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        Product product = this.productService.getProduct(id);

        this.productService.deleteProduct(product);

        return String.format("redirect:/product/detail/%d", product.getId());
    }


}
