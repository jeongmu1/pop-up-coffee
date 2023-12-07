package com.db8.popupcoffee.inquiry.repository;

import com.db8.popupcoffee.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByFaqTrue();
}
