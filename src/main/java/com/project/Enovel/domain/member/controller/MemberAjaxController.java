package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberAjaxRepository;
import com.project.Enovel.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberAjaxController {

    @Autowired
    private MemberAjaxRepository memberAjaxRepository;

    @GetMapping("/checkDuplicateUsername")
    public String checkDuplicateUsername(@RequestParam String username) {
        // 데이터베이스에서 사용자 정보 조회
        Member member = memberAjaxRepository.findByUsername(username);

        if (member != null) {
            return "DUPLICATE";  // 중복된 경우
        } else {
            return "AVAILABLE";  // 중복되지 않은 경우
        }
    }
}
