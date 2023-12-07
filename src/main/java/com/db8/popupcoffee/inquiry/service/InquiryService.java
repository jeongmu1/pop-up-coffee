package com.db8.popupcoffee.inquiry.service;

import com.db8.popupcoffee.inquiry.domain.Inquiry;
import com.db8.popupcoffee.inquiry.domain.InquiryCategory;
import com.db8.popupcoffee.inquiry.domain.InquiryComment;
import com.db8.popupcoffee.inquiry.dto.request.InquiryCommentRequest;
import com.db8.popupcoffee.inquiry.dto.request.InquiryRequest;
import com.db8.popupcoffee.inquiry.repository.InquiryCategoryRepository;
import com.db8.popupcoffee.inquiry.repository.InquiryCommentRepository;
import com.db8.popupcoffee.inquiry.repository.InquiryRepository;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryCategoryRepository inquiryCategoryRepository;
    private final InquiryCommentRepository inquiryCommentRepository;
    private final MerchantRepository merchantRepository;

    public List<Inquiry> getFaqList() {
        return inquiryRepository.findByFaqTrue();
    }

    public void writeInquiry(InquiryRequest form, Long categoryId, Long merchantId) {
        InquiryCategory category = inquiryCategoryRepository.findById(categoryId).orElseThrow();
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow();

        Inquiry inquiry = form.toEntity();
        inquiry.setCategory(category);
        inquiry.setMerchant(merchant);

        inquiryRepository.save(inquiry);
    }

    public void writeComment(Long inquiryId, InquiryCommentRequest form) {
        Inquiry inquiry = getInquiryById(inquiryId);

        InquiryComment inquiryComment = form.toEntity();
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
