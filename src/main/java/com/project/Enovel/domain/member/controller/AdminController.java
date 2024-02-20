package com.project.Enovel.domain.member.controller;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.AdminService;
import com.project.Enovel.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member")
    public String list(Model model, HttpServletRequest request, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Member> paging = this.memberService.getList(page, kw);
        Member loginUser = this.memberService.getMember(principal.getName());

        if(loginUser.isCheckedAdmin()) {
            model.addAttribute("request", request);
            model.addAttribute("paging",paging);
            return "member/admin/memberList";
        }
        return "redirect:/main";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String deleteMember(Principal principal, @RequestParam("username") String username) {
        Member loginUser = this.memberService.getMember(principal.getName());
        if(loginUser.isCheckedAdmin()) {
            memberService.deleteMember(username);
        }
        return "redirect:/admin/member";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/seller/{id}")
    public String sellerMember(Principal principal, @RequestParam("username") String username) {
        Member loginUser = this.memberService.getMember(principal.getName());
        if(loginUser.isCheckedAdmin()) {
            memberService.sellerMember(username);
        }
        return "redirect:/admin/member";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/common/{id}")
    public String commonMember(Principal principal, @RequestParam("username") String username) {
        Member loginUser = this.memberService.getMember(principal.getName());
        if(loginUser.isCheckedAdmin()) {
            memberService.commonMember(username);
        }
        return "redirect:/admin/member";
    }

}
