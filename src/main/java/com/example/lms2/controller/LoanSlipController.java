package com.example.lms2.controller;

import com.example.lms2.dto.CreateLoanSlip;
import com.example.lms2.dto.LoanSlipDto;
import com.example.lms2.entity.Book;
import com.example.lms2.entity.Librarian;
import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.Reader;
import com.example.lms2.service.BookService;
import com.example.lms2.service.LibrarianService;
import com.example.lms2.service.LoanSlipService;
import com.example.lms2.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LoanSlipController {

    @Autowired
    BookService bookService;
    @Autowired
    ReaderService readerService;
    @Autowired
    LibrarianService librarianService;
    @Autowired
    LoanSlipService loanSlipService;
    @GetMapping("/home/loan-slip/create-loan")
    public String getCreateLoan(Model model) {
        LocalDate date = LocalDate.now();
        LocalDate dueDate = date.plusDays(7);
        model.addAttribute("createDate", date);
        model.addAttribute("dueDate", dueDate);
        return "slip-view/create-loan";
    }

    @GetMapping("/home/loan-slip/loan-slips")
    public ModelAndView getLoanSlip(@RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView( "slip-view/loan-slip");
        Page<LoanSlip> loanSlips = loanSlipService.getAll(pageNo, pageSize);
        List<LoanSlipDto> loans = new ArrayList<>();
        for(LoanSlip loanSlip : loanSlips.getContent()){
            loans.add(new LoanSlipDto(loanSlip));
        }
        Page<LoanSlipDto> dtos= new PageImpl<>(loans, loanSlips.getPageable(), loanSlips.getTotalElements());
        modelAndView.addObject("loanSlips", dtos);
        return modelAndView;
    }

    @GetMapping("/home/loan-slip/find-loan")
    public String getFindLoan() {
        return "slip-view/find-loan";
    }
    @PostMapping("/home/loan-slip/find-loan")
    public ModelAndView findLoan(@RequestParam(value = "id",required = false) Long id,
                                 @RequestParam(value = "idReader", required = false) Long idReader){
        ModelAndView modelAndView = new ModelAndView("slip-view/find-loan");
        Reader reader = null;
        if(idReader != null){
            reader= readerService.getById(idReader);
        }
        List<LoanSlipDto> dtos = new ArrayList<>();
        for (LoanSlip loanSlip : loanSlipService.getByIdOrReader(id, reader)){
            dtos.add(new LoanSlipDto(loanSlip));
        }
        modelAndView.addObject("id", id );
        modelAndView.addObject("idReader", idReader);
        modelAndView.addObject("loanSlips", dtos);
        return modelAndView;
    }

    @PostMapping("/api/create-loan")
    public ResponseEntity createLoan(@RequestBody CreateLoanSlip createLoanSlip) {
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
                    book.setRemainingNumber(book.getRemainingNumber()+1);
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
        for (Book book: loanSlip.getBookSet()){
            book.setRemainingNumber(book.getRemainingNumber()+1);
            bookService.updateBook(book);
        }
        LoanSlipDto response = new LoanSlipDto(loanSlip);
        return ResponseEntity.ok(response);
    }

    @RequestMapping("/api/create-loan/add-book")
    public ResponseEntity getBook(@RequestBody String code) {
        Book book = bookService.getByBookCode(code);
        return ResponseEntity.ok(book);
    }


}