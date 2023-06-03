package com.example.lms2.controller;

import com.example.lms2.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class HomeController {
    @Autowired
    LibrarianService librarianService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (!librarianService.isUser(username, password)) {
            return "login";
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }


    @GetMapping("/home/pay-slip/pay-slips")
    public String getPaySlip() {
        return "slip-view/pay-slip";
    }

    @GetMapping("/home/pay-slip/find-pay")
    public String getPayLoan() {
        return "slip-view/find-pay";
    }

}
