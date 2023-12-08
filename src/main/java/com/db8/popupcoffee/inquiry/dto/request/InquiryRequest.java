package com.db8.popupcoffee.inquiry.dto.request;

import com.db8.popupcoffee.inquiry.domain.Inquiry;

public record InquiryRequest(
        String title,
        String content,
        boolean faq
) {

    public Inquiry toEntity() {
        return Inquiry.builder()
                .content(content)
                .faq(false)
                .title(title)
                .build();
    }
}
