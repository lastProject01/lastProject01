package com.project.Enovel.domain.member.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
@Service
@RequiredArgsConstructor
public class SellerService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member sellerCreate(String username, String password, String nickname, String email,
                         String address, String phone) {
        Member seller = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .address(address)
                .phone(phone)
                .checkedSeller(true)
                .build();
        this.memberRepository.save(seller);
        return seller;
    }

    public Member getMember(String username) {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
