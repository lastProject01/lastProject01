package com.project.Enovel.domain.member.controller;


import com.project.Enovel.domain.member.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {


    @PreAuthorize("isAuthenticated()")
    @GetMapping("list")
    public String sellerPage() {
        return "seller/seller_list";
    }
}
