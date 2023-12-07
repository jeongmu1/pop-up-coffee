package com.db8.popupcoffee.inquiry.service;

import com.db8.popupcoffee.inquiry.domain.Inquiry;
import com.db8.popupcoffee.inquiry.domain.InquiryCategory;
import com.db8.popupcoffee.inquiry.domain.InquiryComment;
import com.db8.popupcoffee.inquiry.dto.request.InquiryCommentRequest;
import com.db8.popupcoffee.inquiry.dto.request.InquiryRequest;
import com.db8.popupcoffee.inquiry.repository.InquiryCategoryRepository;
import com.db8.popupcoffee.inquiry.repository.InquiryCommentRepository;
import com.db8.popupcoffee.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryCategoryRepository inquiryCategoryRepository;
    private final InquiryCommentRepository inquiryCommentRepository;

    public List<Inquiry> getFaqList() {
        return inquiryRepository.findByFaqTrue();
    }

    public void writeInquiry(InquiryRequest inquiryForm, Long categoryId) {
        InquiryCategory category = inquiryCategoryRepository.findById(categoryId).orElseThrow();

        Inquiry inquiry = inquiryForm.toEntity();
        inquiry.setCategory(category);

        inquiryRepository.save(inquiry);
    }

    public void writeComment(Long inquiryId, InquiryCommentRequest inquiryCommentForm) {
        Inquiry inquiry = getInquiryById(inquiryId);

        InquiryComment inquiryComment = inquiryCommentForm.toEntity();
        inquiryComment.setInquiry(inquiry);

        inquiryCommentRepository.save(inquiryComment);
    }

    public Inquiry getInquiryById(Long inquiryId) {
        return inquiryRepository.findById(inquiryId).orElseThrow();
    }

    public List<InquiryCategory> getCategories() {
        return inquiryCategoryRepository.findAll();
    }


    public List<InquiryComment> getInquiryComment(Long inquiryId) {
        return inquiryCommentRepository.findByInquiryId(inquiryId);
    }

}
