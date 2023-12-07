package com.db8.popupcoffee.inquiry.repository;

import com.db8.popupcoffee.inquiry.domain.InquiryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCategoryRepository extends JpaRepository<InquiryCategory, Long> {
}
