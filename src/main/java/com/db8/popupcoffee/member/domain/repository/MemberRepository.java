package com.db8.popupcoffee.member.domain.repository;

import com.db8.popupcoffee.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
