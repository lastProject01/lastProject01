package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @Autowired
    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/check-username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsername(@PathVariable String username) {
        return memberService.isUsernameUnique(username);
    }
}