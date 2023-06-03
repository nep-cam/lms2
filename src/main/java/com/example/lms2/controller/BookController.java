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
    @GetMapping("/home/book/books")
    public ModelAndView getBook(@RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Book> books = bookService.getAll(pageNo, pageSize);
        ModelAndView modelAndView = new ModelAndView("book-view/book");
        modelAndView.addObject("books", books);
        return modelAndView;
    }
    @GetMapping("/home/book/add-book")
    public String getAddBook() {
        return "book-view/add-book";
    }


    @PostMapping("/home/book/add-book")
    public ModelAndView addBook(@ModelAttribute() Book book){
        ModelAndView modelAndView =new ModelAndView("book-view/add-book");
        if(bookService.checkBook(book)) {
            bookService.createdBook(book);
            modelAndView.addObject("successMsg", "Success add a new book!");
        }
        else {
            modelAndView.addObject("errorMsg", "Fail add a book. The book code is exist!");
        }
        return modelAndView;
    }

    @GetMapping("/home/book/find-book")
    public String getFindBook() {
        return "book-view/find-book";
    }

    @PostMapping ("/home/book/find-book")
    public ModelAndView searchBook(@RequestParam("keyword") String keyword) {
        List<Book> books = bookService.searchBook(keyword);
        ModelAndView modelAndView = new ModelAndView( "book-view/find-book");
        modelAndView.addObject("books", books);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
}