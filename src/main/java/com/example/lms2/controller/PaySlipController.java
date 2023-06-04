package com.example.lms2.controller;

import com.example.lms2.dto.LoanSlipDto;
import com.example.lms2.dto.PaySlipDto;
import com.example.lms2.entity.Book;
import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.PaySlip;
import com.example.lms2.entity.Reader;
import com.example.lms2.service.BookService;
import com.example.lms2.service.LoanSlipService;
import com.example.lms2.service.PaySlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PaySlipController {
    @Autowired
    BookService bookService;
    @Autowired
    PaySlipService paySlipService;
    @Autowired
    LoanSlipService loanSlipService;

    @PostMapping("/home/pay-slip/find-pay")
    public ModelAndView findPaySlip(@RequestParam(value = "idPay",required = false) Long idPay,
                                    @RequestParam(value = "idLoan", required = false) Long idLoan,@RequestParam("username") String username){
        ModelAndView modelAndView = new ModelAndView("slip-view/find-pay");
        LoanSlip loanSlip = null;
        if(idLoan != null){
            loanSlip= loanSlipService.getById(idLoan);
        }
        List<PaySlipDto> dtos = new ArrayList<>();
        for (PaySlip paySlip: paySlipService.getByIdOrLoanSlip(idPay, loanSlip)){
            dtos.add(new PaySlipDto(paySlip));
        }
        modelAndView.addObject("idPay", idPay );
        modelAndView.addObject("idLoan", idLoan);
        modelAndView.addObject("paySlips", dtos);
        modelAndView.addObject("username", username);

        return modelAndView;
    }



    @PostMapping("/home/pay-slip/create-pay")
    public ModelAndView findLoanSlip(@RequestParam("idSlip") Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date = LocalDateTime.now().format(formatter);
        ModelAndView modelAndView = new ModelAndView("slip-view/create-pay");
        LoanSlip loanSlip = loanSlipService.getById(id);
        List<Book> books = paySlipService.checkBookToPay(loanSlip);
        modelAndView.addObject("loanSlip", new LoanSlipDto(loanSlip));
        modelAndView.addObject("booksToPay", books);
        modelAndView.addObject("idSlip", id);
        modelAndView.addObject("datetime", date);
        return modelAndView;
    }
    @PostMapping("/api/pay-slip/find-loan-slip")
    public ResponseEntity findLoan(@RequestBody Long id){
        LoanSlip loanSlip = loanSlipService.getById(id);
        return ResponseEntity.ok(loanSlip);
    }
    @PostMapping("/api/pay-slip/check-book")
    public ResponseEntity checkBookToPay(@RequestBody Long id){
        LoanSlip loanSlip = loanSlipService.getById(id);
        List<Book> books = paySlipService.checkBookToPay(loanSlip);
        return ResponseEntity.ok(books);
    }
    @PostMapping("/api/create-pay")
    public ResponseEntity createPaySlip(@RequestParam Long idSlip, @RequestParam List<String> books, @RequestParam String librarianName){
        PaySlip paySlip = paySlipService.createPaySlip(idSlip, books, librarianName);
        for (Book book: paySlip.getBookSet()){
            book.setRemainingNumber(book.getRemainingNumber()-1);
            bookService.updateBook(book);
        }
        LoanSlip loanSlip = loanSlipService.getById(idSlip);
        List<Book> book = paySlipService.checkBookToPay(loanSlip);
        if(book.size()==0){
            loanSlip.setStatus("da tra");
            loanSlipService.updateLoanSlip(loanSlip);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String msg ="Trả sách thành công\n Thời gian: "+paySlip.getCreatedDate().format(formatter);;
        return ResponseEntity.ok(msg);
    }
}
