package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.form.MemberCreateForm;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    private void validatePassword(String password1, String password2, BindingResult bindingResult) {
        if (!password1.equals(password2)) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
        }
    }

    private void handleUserCreationError(BindingResult bindingResult) {
        bindingResult.reject("signupFailed", "이미 가입된 사용자입니다.");
    }

    @GetMapping("login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm) {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        validatePassword(memberCreateForm.getPassword1(), memberCreateForm.getPassword2(), bindingResult);

        try {
            memberService.create(memberCreateForm.getUsername(), memberCreateForm.getPassword1(), memberCreateForm.getNickname(),
                    memberCreateForm.getEmail(), memberCreateForm.getAddress(), memberCreateForm.getPhone(), false, false);
        } catch (DataIntegrityViolationException e) {
            handleUserCreationError(bindingResult);
        }

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/showmypage")
    public String showmypage(UserMypageForm userMypageForm, UserCreateForm userCreateForm, Principal principal) {
        String username = principal.getName();
        SiteUser siteUser = this.userService.getUser(username);
        if (!siteUser.getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        userCreateForm.setUserId(siteUser.getUserId());
        userMypageForm.setNickname(siteUser.getNickname());
        userMypageForm.setPhone(siteUser.getPhone());

        return "user/mypage_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypage(UserMypageForm userMypageForm, UserCreateForm userCreateForm, Principal principal) {
        String username = principal.getName();
        SiteUser siteUser = this.userService.getUser(username);
        if (!siteUser.getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        userCreateForm.setUserId(siteUser.getUserId());
        userMypageForm.setNickname(siteUser.getNickname());
        userMypageForm.setPhone(siteUser.getPhone());
        return "user/mypage_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/mypage")
    public String userModify(@Valid UserMypageForm userMypageForm,
                             BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "user/mypage_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (!siteUser.getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        try {
            userService.modify(siteUser, userMypageForm.getNickname(), userMypageForm.getPhone());
            return "redirect:/user/showmypage";
        } catch (Exception e) {
            e.printStackTrace();
            return "user/mypage_form";
        }
    }
}
