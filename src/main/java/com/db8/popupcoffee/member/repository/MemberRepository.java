package com.db8.popupcoffee.member.repository;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.merchant.domain.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByAuthenticationInfo(AuthenticationInfo authenticationInfo);
}
