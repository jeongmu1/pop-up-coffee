package com.db8.popupcoffee.inquiry.controller;

import com.db8.popupcoffee.inquiry.domain.Inquiry;
import com.db8.popupcoffee.inquiry.domain.InquiryCategory;
import com.db8.popupcoffee.inquiry.domain.InquiryComment;
import com.db8.popupcoffee.inquiry.dto.request.InquiryCommentRequest;
import com.db8.popupcoffee.inquiry.dto.request.InquiryRequest;
import com.db8.popupcoffee.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping("/faqList")
    public String getFaqList(Model model) {
        List<Inquiry> faqList = inquiryService.getFaqList();
        model.addAttribute("faqList", faqList);

        return "inquiry/faqList";
    }

    @GetMapping("/write")
    public String writeInquiry(Model model) {
        List<InquiryCategory> categories = inquiryService.getCategories();
        model.addAttribute("categories", categories);

        return "inquiry/write";
    }

    @PostMapping("/write")
    public String writeInquiry(InquiryRequest inquiryForm, Long categoryId) {
        inquiryService.writeInquiry(inquiryForm, categoryId);

        return "inquiry/list";
    }

    @GetMapping("/{inquiryId}")
    public String getInquiry(@PathVariable Long inquiryId, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(inquiryId);
        List<InquiryComment> inquiryComments = inquiryService.getInquiryComment(inquiryId);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("inquiryComments", inquiryComments);

        return "inquiry/detail";
    }

    @PostMapping("/{inquiryId}/comment")
    public String writeComment(@PathVariable Long inquiryId, InquiryCommentRequest inquiryCommentRequest) {
        inquiryService.writeComment(inquiryId, inquiryCommentRequest);

        return "redirect:/inquiry/" + inquiryId;
    }


}