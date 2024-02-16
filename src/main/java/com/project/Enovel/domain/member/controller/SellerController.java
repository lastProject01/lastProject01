package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.form.MemberCreateForm;
import com.project.Enovel.domain.member.form.SellerCreateForm;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;
    private final PasswordEncoder passwordEncoder;

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
        return "seller/signup";
    }

    @PostMapping("/signup")
    public String signup(SellerCreateForm sellerCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "seller/signup";
        }

        validatePassword(sellerCreateForm.getPassword1(), sellerCreateForm.getPassword2(), bindingResult);

        try {
            sellerService.sellerCreate(sellerCreateForm.getUsermane(), sellerCreateForm.getPassword1(), sellerCreateForm.getNickname(),
                    sellerCreateForm.getEmail(), sellerCreateForm.getAddress(), sellerCreateForm.getPhone());
        } catch (DataIntegrityViolationException e) {
            handleUserCreationError(bindingResult);
        }

        return "redirect:/";
    }
}
