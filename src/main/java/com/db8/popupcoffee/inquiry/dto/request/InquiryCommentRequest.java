package com.db8.popupcoffee.inquiry.dto.request;

import com.db8.popupcoffee.inquiry.domain.CommentWriter;
import com.db8.popupcoffee.inquiry.domain.InquiryComment;


public record InquiryCommentRequest(
        String content,
        CommentWriter writer
) {
    public InquiryComment toEntity() {
        return InquiryComment.builder()
                .content(content)
                .writer(writer)
                .build();
    }

}
