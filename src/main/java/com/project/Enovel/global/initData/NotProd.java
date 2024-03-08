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
//            memberService.create("admin1", "123123", "관리자1", "admin1@enovel.com", "대전광역시 서구", "01011111111", true, false, false);
//            memberService.create("admin2", "123123", "관리자2", "admin2@enovel.com", "대전광역시 동구", "01022222222", true, false, false);
//            memberService.create("seller1", "123123", "책벌레상점", "seller1@enovel.com", "대전광역시 대덕구", "01011111111", false, true, false);
//            memberService.create("seller2", "123123", "책을낳는나무", "seller2@enovel.com", "대전광역시 유성구", "01022222222", false, true, false);
        };
    }
};