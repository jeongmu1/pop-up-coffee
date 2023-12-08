package com.db8.popupcoffee.member.service;

import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.db8.popupcoffee.global.util.Policy.SURVEY_REWARD;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void increasePointAndSetLastSurveyed(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(null);
        member.setPoint(member.getPoint() + SURVEY_REWARD);
        member.setLastSurveyed(LocalDateTime.now());

        memberRepository.save(member);
    }
}
