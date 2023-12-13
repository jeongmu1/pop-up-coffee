package com.db8.popupcoffee.member.repository;

import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    PointHistory findTopByMemberOrderByIdDesc(Member member);
}
