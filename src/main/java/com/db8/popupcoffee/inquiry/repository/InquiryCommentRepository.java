package com.db8.popupcoffee.inquiry.repository;

import com.db8.popupcoffee.inquiry.domain.InquiryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryCommentRepository extends JpaRepository<InquiryComment, Long> {
    List<InquiryComment> findByInquiryId(Long inquiryId);
}
