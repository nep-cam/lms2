package com.example.lms2.controller;

import com.example.lms2.dto.LoanSlipDto;
import com.example.lms2.dto.PaySlipDto;
import com.example.lms2.entity.*;
import com.example.lms2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    LibrarianService librarianService;
    @Autowired
    BookService bookService;
    @Autowired
    ReaderService readerService;
    @Autowired
    LoanSlipService loanSlipService;
    @Autowired
    PaySlipService paySlipService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        if (!librarianService.isUser(username, password)) {
            return "login";
        }
        Librarian librarian=librarianService.getByUserName(username);
        redirectAttributes.addAttribute("username", librarian.getFullName());
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHome(@RequestParam("username") String username, Model model) {

        model.addAttribute("username", username);
        return "home";
    }


    @GetMapping("/home/book/books")
    public ModelAndView getBook(@RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam("username") String username) {
        Page<Book> books = bookService.getAll(pageNo, pageSize);
        ModelAndView modelAndView = new ModelAndView("book-view/book");
        modelAndView.addObject("books", books);
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("/home/book/add-book")
    public String getAddBook(@RequestParam("username") String username, Model model) {

        model.addAttribute("username", username);

        return "book-view/add-book";
    }

    @GetMapping("/home/book/find-book")
    public String getFindBook(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);

        return "book-view/find-book";
    }

    @GetMapping("/home/loan-slip/create-loan")
    public String getCreateLoan(@RequestParam("username") String username, Model model) {
        LocalDate date = LocalDate.now();
        LocalDate dueDate = date.plusDays(7);
        model.addAttribute("createDate", date);
        model.addAttribute("dueDate", dueDate);
        model.addAttribute("username", username);

        return "slip-view/create-loan";
    }

    @GetMapping("/home/loan-slip/loan-slips")
    public ModelAndView getLoanSlip(@RequestParam(defaultValue = "0") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam("username") String username) {
        ModelAndView modelAndView = new ModelAndView("slip-view/loan-slip");
        Page<LoanSlip> loanSlips = loanSlipService.getAll(pageNo, pageSize);
        List<LoanSlipDto> loans = new ArrayList<>();
        for (LoanSlip loanSlip : loanSlips.getContent()) {
            loans.add(new LoanSlipDto(loanSlip));
        }
        Page<LoanSlipDto> dtos = new PageImpl<>(loans, loanSlips.getPageable(), loanSlips.getTotalElements());
        modelAndView.addObject("loanSlips", dtos);
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("/home/loan-slip/find-loan")
    public String getFindLoan(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);

        return "slip-view/find-loan";
    }

    @GetMapping("/home/pay-slip/pay-slips")
    public ModelAndView getPaySlip(@RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam("username") String username) {
        ModelAndView modelAndView = new ModelAndView("slip-view/pay-slip");
        Page<PaySlip> paySlips = paySlipService.getAll(pageNo, pageSize);
        List<PaySlipDto> dtos1 = new ArrayList<>();
        for (PaySlip loanSlip : paySlips.getContent()) {
            dtos1.add(new PaySlipDto(loanSlip));
        }
        Page<PaySlipDto> dtos = new PageImpl<>(dtos1, paySlips.getPageable(), paySlips.getTotalElements());
        modelAndView.addObject("paySlips", dtos);
        modelAndView.addObject("username", username);

        return modelAndView;
    }

    @GetMapping("/home/pay-slip/find-pay")
    public String getFindPay(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);

        return "slip-view/find-pay";
    }

    @GetMapping("/home/pay-slip/create-pay")
    public String getCreatePay(@RequestParam("username") String username, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date = LocalDateTime.now().format(formatter);
        model.addAttribute("datetime", date);
        model.addAttribute("username", username);
        return "slip-view/create-pay";
    }

    @GetMapping("home/reader/readers")
    public ModelAndView getReader(@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam("username") String username) {
        ModelAndView modelAndView = new ModelAndView("reader-view/reader");
        Page<Reader> readers = readerService.getAll(pageNo, pageSize);
        modelAndView.addObject("readers", readers);
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @GetMapping("home/reader/add-reader")
    public String getAddReader(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "reader-view/add-reader";
    }

    @GetMapping("home/reader/find-reader")
    public String getFindReader(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "reader-view/find-reader";
    }
}
