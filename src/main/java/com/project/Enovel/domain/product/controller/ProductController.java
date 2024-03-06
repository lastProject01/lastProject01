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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MemberService memberService;

    //상품 세부
    @GetMapping("/detail/{id}")
    public String detailProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = this.productService.getProduct(id);

        model.addAttribute("product", product);

        return "product/product_detail";
    }


    //admin 등급과 seller 등급만 접근 가능
    //상품 생섬 폼 이동
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("/create")
    public String createProduct(ProductCreateForm productCreateForm, Principal principal) {

        Member member = this.memberService.getMember(principal.getName());

        //회원 등급 검증
        //user 등급만 필터링
        if (!member.isCheckedAdmin() && !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        return "product/product_create";
    }

    //admin 등급과 seller 등급만 접근 가능
    //상품 생성
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @PostMapping("/create")
    public String createProductPost(@Valid ProductCreateForm productCreateForm, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Principal principal) throws IOException {

        Member member = this.memberService.getMember(principal.getName());

        //회원 등급 검증
        //user 등급만 필터링
        if (!member.isCheckedAdmin() && !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "redirect:/product/create";
        }


        this.productService.createProduct(productCreateForm.getProductName(),
                productCreateForm.getPrice(),
                productCreateForm.getProductImg(),
                productCreateForm.getContent(),
                file,
                member);

        return "redirect:/";
    }

    //admin 등급과 seller 등급만 접근 가능
    //수정 폼 이동
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, ProductCreateForm productCreateForm, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());

        //회원 등급 검증
        //user등급만 필터링
        if (!member.isCheckedAdmin() && !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        Product product = this.productService.getProduct(id);
        return "product/product_modify";
    }

    //admin 등급과 seller 등급만 접근 가능
    //상품 수정
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @PostMapping("/modify/{id}")
    public String modifyProduct(@PathVariable(value = "id") Long id, @Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal) {

        Member member = this.memberService.getMember(principal.getName());

        //회원 등급 검증
        if (!member.isCheckedAdmin() && !member.isCheckedSeller() ) {
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

    // 상품 삭제 컨트롤러 메서드
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id, Principal principal) {
        // TODO: 회원 등급 검증 문제 해결 필요(비회원이 기능 사용 시 기능 정상 작동 문제)

        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        Member member = this.memberService.getMember(principal.getName());

        Product product = this.productService.getProduct(id);

        // 회원 등급 검증
        // user 등급만 필터링
        if (member.isCheckedAdmin() || (member.isCheckedSeller() && product.getAuthor().equals(member) && member.getUsername().equals(principal.getName()))) {
            // 현재 사용자가 관리자거나 판매자이면서 상품 작성자와 동일한 경우에만 삭제 처리
            this.productService.deleteProduct(product, member, principal);
        } else {
            // 권한이 없는 경우 익셉션 발생
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "상품을 삭제할 권한이 없습니다.");
        }

        return "redirect:/seller/products";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    @PostMapping("/add/{id}")
    public String addProduct(@PathVariable(value = "id")Long id, Principal principal) {

        Member member = this.memberService.getMember(principal.getName());

        //회원 등급 검증
        //user등급만 필터링
        if (!member.isCheckedAdmin() && !member.isCheckedSeller() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        Product product = this.productService.getProduct(id);

        this.productService.addProduct(product, member, principal);

        return "redirect:/seller/products";
    }


}
