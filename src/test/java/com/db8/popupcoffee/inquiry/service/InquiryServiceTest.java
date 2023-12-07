package com.db8.popupcoffee.inquiry.service;

import com.db8.popupcoffee.inquiry.repository.InquiryCategoryRepository;
import com.db8.popupcoffee.inquiry.repository.InquiryCommentRepository;
import com.db8.popupcoffee.inquiry.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
public class InquiryServiceTest {
    @Autowired
    InquiryRepository inquiryRepository;
    @Autowired
    InquiryCommentRepository inquiryCommentRepository;
    @Autowired
    InquiryService inquiryService;
    @Autowired
    InquiryCategoryRepository inquiryCategoryRepository;

//    @Test
//    public void testWriteInquiry() {
//        InquiryCategory category = new InquiryCategory();
//        category.setCategory("경고");
//        inquiryCategoryRepository.save(category);
//
//        InquiryForm inquiryForm = new InquiryForm(); // set properties as needed
//        inquiryForm.setTitle("제목");
//        inquiryForm.setContent("내용");
//        inquiryForm.setCategory(category);
//
//        Inquiry savedInquiry = inquiryService.writeInquiry(inquiryForm, categoryId);
//
//        // 데이터베이스에서 Inquiry 조회
//        Optional<Inquiry> retrievedInquiryOptional = inquiryRepository.findById(savedInquiry.getId());
//        assertTrue(retrievedInquiryOptional.isPresent());
//
//        Inquiry retrievedInquiry = retrievedInquiryOptional.get();
//        // 저장된 Inquiry의 속성들이 우리가 저장하려고 했던 값들과 일치하는지 확인
//        assertEquals(inquiryForm.getTitle(), retrievedInquiry.getTitle());
//        assertEquals(inquiryForm.getContent(), retrievedInquiry.getContent());
//        assertEquals(inquiryForm.getCategory().getCategory(), retrievedInquiry.getCategory().getCategory());
//
//        System.out.println(inquiryForm.getCategory().getCategory() + retrievedInquiry.getCategory().getCategory());
//    }



//    @Test
//    public void testWriteComment() {
//        InquiryCategory category = new InquiryCategory();
//        category.setCategory("경고");
//        inquiryCategoryRepository.save(category);
//
//        // 먼저 문의글을 하나 저장합니다.
//        Inquiry inquiry = new Inquiry();
//        inquiry.setTitle("제목");
//        inquiry.setContent("내용");
//        inquiry.setCategory(category);
//        inquiry.setFaq(false);
//        Inquiry savedInquiry = inquiryRepository.save(inquiry);
//
//        // 댓글 폼을 생성하고 필요한 속성을 설정합니다.
//        InquiryCommentForm commentForm = new InquiryCommentForm();
//        commentForm.setContent("댓글 내용");
//        commentForm.setWriter(CommentWriter.ADMIN);
//
//        // 댓글을 저장하고 저장된 인스턴스를 받습니다.
//        InquiryComment savedComment = inquiryService.writeComment(savedInquiry.getId(), commentForm);
//
//        // 데이터베이스에서 저장한 문의글을 조회합니다.
//        Optional<Inquiry> retrievedInquiryOptional = inquiryRepository.findById(savedInquiry.getId());
//        assertTrue(retrievedInquiryOptional.isPresent());
//
//        // 데이터베이스에서 댓글을 조회합니다.
//        Optional<InquiryComment> retrievedCommentOptional = inquiryCommentRepository.findById(savedComment.getId());
//        assertTrue(retrievedCommentOptional.isPresent());
//
//        // 댓글이 해당하는 문의글에 속하였는지 확인합니다.
//        assertEquals(savedInquiry.getId(), retrievedCommentOptional.get().getInquiry().getId());
//    }



}

