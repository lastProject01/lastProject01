package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;
    private final SellerService sellerService;
    private final PasswordEncoder passwordEncoder;


}
