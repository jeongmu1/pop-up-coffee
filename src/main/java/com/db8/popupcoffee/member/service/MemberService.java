package com.db8.popupcoffee.member.service;

import com.db8.popupcoffee.member.controller.dto.request.CreateMemberRequest;
import com.db8.popupcoffee.member.controller.dto.request.MemberLoginForm;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.db8.popupcoffee.global.util.Policy.SURVEY_REWARD;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(CreateMemberRequest form) {
        memberRepository.save(form.toEntity());
    }

    @Transactional
    public Member findMember(MemberLoginForm form) {
        return memberRepository.findMemberByAuthenticationInfo(form.toAuthenticationInfo()).orElseThrow(null);
    }

    @Transactional
    public void increasePointAndSetLastSurveyed(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(null);
        member.setPoint(member.getPoint() + SURVEY_REWARD);
        member.setLastSurveyed(LocalDateTime.now());

        memberRepository.save(member);
    }



}
