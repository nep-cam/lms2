package com.example.lms2.controller;

import com.example.lms2.dto.CreateLoanSlip;
import com.example.lms2.entity.Book;
import com.example.lms2.entity.Librarian;
import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.Reader;
import com.example.lms2.service.BookService;
import com.example.lms2.service.LibrarianService;
import com.example.lms2.service.LoanSlipService;
import com.example.lms2.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SlipController {

    @Autowired
    BookService bookService;
    @Autowired
    ReaderService readerService;
    @Autowired
    LibrarianService librarianService;
    @Autowired
    LoanSlipService loanSlipService;

    @PostMapping("/home/create-loan")
    public ResponseEntity createLoan(@RequestBody CreateLoanSlip createLoanSlip, Model model) {
        LoanSlip loanSlip = new LoanSlip();
        Librarian librarian = librarianService.getById(createLoanSlip.getLibrarianId());
        Reader reader = readerService.getById(createLoanSlip.getReaderId());
        LocalDateTime creatDate = LocalDateTime.now();
        LocalDate dueDate= createLoanSlip.getDueDate();
        Set<Book> books = new HashSet<>();
        for (String bookCode : createLoanSlip.getBookCodes()) {
            if (bookCode != null && !bookCode.isEmpty()) {
                Book book = bookService.getByBookCode(bookCode);
                if (book != null) {
                    books.add(book);
                }
            }
        }
        loanSlip.setLibrarian(librarian);
        loanSlip.setReader(reader);
        loanSlip.setBookSet(books);
        loanSlip.setCreateDate(creatDate);
        loanSlip.setDueDate(dueDate);
        loanSlip.setStatus("dang muon");
        loanSlipService.createdLoanSlip(loanSlip);
        return ResponseEntity.ok(loanSlip);
    }

    @GetMapping("/home/create-loan")
    public String getCreateLoan(Model model) {

        LocalDate date = LocalDate.now();
        LocalDate dueDate = date.plusDays(7);
        model.addAttribute("createDate", date);
        model.addAttribute("dueDate", dueDate);
        return "slip-view/create-loan";
    }

    @RequestMapping("/home/slip/add-book")
    public ResponseEntity getBook(@RequestBody String code) {
        Book book = bookService.getByBookCode(code);
        return ResponseEntity.ok(book);
    }
}
