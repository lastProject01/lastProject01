package com.project.Enovel.global.initData;

import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class NotProd {
    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    public ApplicationRunner init(MemberService memberService, ProductService productService) {
        return args -> {

//            productService.createProduct("샘플 상품 1", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 2", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 3", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 4", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 5", 10000, "sample.jpg", "샘플 상품 설명입니다.");
        };
    }
};