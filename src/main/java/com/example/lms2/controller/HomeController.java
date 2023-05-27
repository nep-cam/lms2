package com.example.lms2.controller;

import com.example.lms2.entity.Book;
import com.example.lms2.service.BookService;
import com.example.lms2.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    LibrarianService librarianService;
    @Autowired
    BookService bookService;

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username")String username, @RequestParam("password") String password ){
        if(!librarianService.isUser(username, password)){
            return "login";
        }
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @RequestMapping("/home/books")
    public ModelAndView getBook(@RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Book> books= bookService.getAll(pageNo, pageSize);
        ModelAndView modelAndView = new ModelAndView( "book-view/book");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/home/add-book")
    public String getAddBook(){
        return "book-view/add-book";
    }
    @PostMapping("/home/add-book")
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

//    @GetMapping("/home/find-book")
//    public String getFindBook(){
//        return "book-view/find-book";
//    }
    @RequestMapping ("/home/find-book")
    public ModelAndView searchBook(@RequestParam("keyword") String keyword) {
        List<Book> books = bookService.searchBook(keyword);
        ModelAndView modelAndView = new ModelAndView( "book-view/find-book");
        modelAndView.addObject("books", books);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
}
