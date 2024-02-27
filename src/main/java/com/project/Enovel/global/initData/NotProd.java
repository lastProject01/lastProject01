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
//            memberService.create("user1", "123123", "유저1", "user1@enovel.com", "주소", "01011111111", false);
//            memberService.create("user2", "123123", "유저2", "user2@enovel.com", "주소", "01022222222", false);
//            memberService.create("user3", "123123", "유저3", "user3@enovel.com", "주소", "01033333333", false);
//            memberService.create("seller1", "123123", "판매자1", "seller1@enovel.com", "주소", "01011111111", true);
//            memberService.create("seller2", "123123", "판매자2", "seller2@enovel.com", "주소", "01022222222", true);
//            memberService.create("seller3", "123123", "판매자3", "seller3@enovel.com", "주소", "01033333333", true);
//            memberService.create("admin1", "123123", "관리자1", "admin1@enovel.com", "주소", "01011111111", false);
//            memberService.create("admin2", "123123", "관리자2", "admin2@enovel.com", "주소", "01022222222", false;
//            memberService.create("admin3", "123123", "관리자3", "admin3@enovel.com", "주소", "01033333333", false);
//
//            productService.createProduct("샘플 상품 1", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 2", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 3", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 4", 10000, "sample.jpg", "샘플 상품 설명입니다.");
//            productService.createProduct("샘플 상품 5", 10000, "sample.jpg", "샘플 상품 설명입니다.");
        };
    }
};