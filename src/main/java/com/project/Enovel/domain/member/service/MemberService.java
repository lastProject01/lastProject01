package com.project.Enovel.domain.member.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member create(String username, String password, String nickname, String email,
                         String address, String phone, boolean checkedSeller, boolean checkedAdmin) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .address(address)
                .phone(phone)
                .checkedSeller(checkedSeller)
                .checkedAdmin(checkedAdmin)
                .createDate(LocalDateTime.now())
                .build();
        this.memberRepository.save(member);
        return member;
    }

    public Member getMember(Long id) {
        return this.memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public Member getMemberFindByUsername(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        return member.get();
    }
}
