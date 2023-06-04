package com.example.lms2.service;

import com.example.lms2.entity.Book;
import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.PaySlip;
import com.example.lms2.repository.BookRepository;
import com.example.lms2.repository.LoanSlipRepository;
import com.example.lms2.repository.PaySlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaySlipService {
    @Autowired
    private PaySlipRepository paySlipRepository;
    @Autowired
    private LoanSlipRepository loanSlipRepository;
    @Autowired
    private BookRepository bookRepository;

    public PaySlip createdPaySlip(PaySlip paySlip) {
        return paySlipRepository.save(paySlip);
    }

    public PaySlip getById(Long id) {
        return paySlipRepository.findById(id).orElse(null);
    }

    public void updatePaySlip(PaySlip paySlip) {
        paySlipRepository.save(paySlip);
    }

    public Page<PaySlip> getAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return paySlipRepository.findAll(paging);
    }


    public List<Book> checkBookToPay(LoanSlip loanSlip) {
        Set<Book> loanBooks = loanSlip.getBookSet();
        List<Book> bookToPay = new ArrayList<>();
        bookToPay.addAll(loanBooks);
        List<PaySlip> paySlips = paySlipRepository.findByLoanSlip(loanSlip);
        for (Book book : loanBooks) {
            for (PaySlip pay : paySlips) {
                Set<Book> books = pay.getBookSet();
                if (books.contains(book)) {
                    bookToPay.remove(book);
                    break;
                }
            }
        }
        return bookToPay;
    }

    public PaySlip createPaySlip(Long idSlip, List<String> bookCodes) {
        PaySlip paySlip = new PaySlip();
        LoanSlip loanSlip = loanSlipRepository.findById(idSlip).orElse(null);

        Set<Book> bookSet = new HashSet<>();
        for (String code : bookCodes) {
            Book book = bookRepository.findByBookCode(code);
            if (book != null){
                bookSet.add(book);
            }
        }

        if(loanSlip != null && bookSet != null){
            paySlip.setLoanSlip(loanSlip);
            paySlip.setCreatedDate(LocalDateTime.now());
            paySlip.setBookSet(bookSet);
            paySlipRepository.save(paySlip);
        }
        return paySlip;
    }
    public List<PaySlip> getByIdOrLoanSlip(Long id, LoanSlip loanSlip){
        return paySlipRepository.findByIdOrLoanSlip(id, loanSlip);
    }
}
