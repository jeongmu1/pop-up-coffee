package com.db8.popupcoffee.member.controller;

import com.db8.popupcoffee.global.util.SessionUtil;
import com.db8.popupcoffee.member.controller.dto.MemberSessionInfo;
import com.db8.popupcoffee.member.controller.dto.request.CreateMemberRequest;
import com.db8.popupcoffee.member.controller.dto.request.MemberLoginForm;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/form")
    public String getMemberCreatingForm() {
        return "members/signup";
    }

    @PostMapping
    public String createMember(CreateMemberRequest form) {
        memberService.createMember(form);
        return "redirect:/members";
    }

    @PostMapping("/login")
    public String progressLogin(MemberLoginForm form, HttpSession session) {
        Member member = memberService.findMember(form);
        session.setAttribute(SessionUtil.MEMBER_SESSION_KEY, MemberSessionInfo.from(member));
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute(SessionUtil.MEMBER_SESSION_KEY, null);
        return "redirect:/";
    }

}
