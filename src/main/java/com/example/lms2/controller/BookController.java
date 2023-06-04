package com.example.lms2.controller;

import com.example.lms2.entity.Book;
import com.example.lms2.service.BookService;
import com.example.lms2.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/home/book/add-book")
    public ModelAndView addBook(@ModelAttribute() Book book, @RequestParam("username") String username){
        ModelAndView modelAndView =new ModelAndView("book-view/add-book");
        if(bookService.checkBook(book)) {
            bookService.createdBook(book);
            modelAndView.addObject("successMsg", "Success add a new book!");

        }
        else {
            modelAndView.addObject("errorMsg", "Fail add a book. The book code is exist!");
        }
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @PostMapping ("/home/book/find-book")
    public ModelAndView searchBook(@RequestParam("keyword") String keyword, @RequestParam("username") String username) {
        List<Book> books = bookService.searchBook(keyword);
        ModelAndView modelAndView = new ModelAndView( "book-view/find-book");
        modelAndView.addObject("books", books);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("username", username);
        return modelAndView;
    }
}
