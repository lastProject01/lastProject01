package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.form.MemberCreateForm;
import com.project.Enovel.domain.member.form.MemberMyPageForm;
import com.project.Enovel.domain.member.form.MemberPasswordForm;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.member.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
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
            return "member/signup";
        }

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myInfo")
    public String myInfo(MemberCreateForm memberCreateForm, Principal principal) {
        String username = principal.getName();
        Member member = this.memberService.getMember(username);
        if (!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        memberCreateForm.setUsername(member.getUsername());
        memberCreateForm.setNickname(member.getNickname());
        memberCreateForm.setEmail(member.getEmail());
        memberCreateForm.setAddress(member.getAddress());
        memberCreateForm.setPhone(member.getPhone());

        return "member/my_info";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/updateMyInfo")
    public String myPage(MemberCreateForm memberCreateForm, Principal principal) {
        String username = principal.getName();
        Member member = this.memberService.getMember(username);
        if (!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        memberCreateForm.setUsername(member.getUsername());
        memberCreateForm.setNickname(member.getNickname());
        memberCreateForm.setEmail(member.getEmail());
        memberCreateForm.setAddress(member.getAddress());
        memberCreateForm.setPhone(member.getPhone());

        return "member/my_info_update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updateMyInfo")
    public String memberModify(@Valid MemberMyPageForm memberMyPageForm,
                               BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "member/my_info_update";
        }
        Member member = this.memberService.getMember(principal.getName());
        if (!member.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        try {
            memberService.modify(member, memberMyPageForm.getNickname(), memberMyPageForm.getEmail(),
                    memberMyPageForm.getAddress(), memberMyPageForm.getPhone());
            return "redirect:/member/myInfo";
        } catch (Exception e) {
            e.printStackTrace();
            return "member/my_info";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/changePw")
    public String changePw(MemberPasswordForm memberPasswordForm) {
        return "member/change_pw";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/changePw")
    public String changePassword(@Valid MemberPasswordForm memberPasswordForm, BindingResult bindingResult, Principal principal, Model model) {
        String username = principal.getName();
        Member member = memberService.getMember(username);

        // 새로운 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (!memberPasswordForm.getNewPassword().equals(memberPasswordForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordInCorrect", "새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "member/change_pw"; // 에러가 있으면 변경 페이지로 다시 이동
        }

        // 비밀번호 변경
        try {
            memberService.changePw(member, memberPasswordForm.getNewPassword());
            return "redirect:/member/myInfo"; // 성공 페이지로 리다이렉트 또는 성공 메시지를 표시하는 뷰로 이동
        } catch (RuntimeException e) {
            model.addAttribute("error", "비밀번호 변경에 실패했습니다.");
            return "redirect:/member/changePw"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 표시하는 뷰로 이동
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/checkedPw")
    public String checkedPw() {
        return "member/checked_pw";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkedPw")
    public String checkedPw(@Valid MemberPasswordForm memberPasswordForm, BindingResult bindingResult, Principal principal, Model model) {
        // 사용자 이름 가져오기
        String username = principal.getName();
        // 사용자 정보 가져오기
        Member member = memberService.getMember(username);

        // 입력된 확인 비밀번호가 실제 사용자의 비밀번호와 일치하지 않으면 에러 추가
        if (!passwordEncoder.matches(memberPasswordForm.getConfirmPassword(), member.getPassword())) {
            bindingResult.rejectValue("currentPassword", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
        }

        // 바인딩 결과에 에러가 있으면 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            return "member/checked_pw";
        }

        // 모든 검증을 통과하면 비밀번호가 일치하므로 성공 페이지로 리다이렉트
        return "redirect:/member/myInfo";
    }


}