package com.project.Enovel.domain.member.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public PasswordService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Transactional
//    public String checkedPw(String confirmPassword, Member member) {
//        Optional<Member> optionalUser = memberRepository.findByUsername(member.getUsername());
//
//        if (optionalUser.isPresent()) {
//            Member checkedMember = optionalUser.get();
//
//            // 기존 비밀번호가 일치하는지 확인
//            if (passwordEncoder.matches(confirmPassword, checkedMember.getPassword())) {
//                return "redirect:/member/myInfo";
//            } else {
//                // 기존 비밀번호가 일치하지 않으면 예외 처리 또는 다른 처리 로직 추가
//                throw new RuntimeException("Invalid current password");
//            }
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
}
