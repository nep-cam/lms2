package com.example.lms2.service;

import com.example.lms2.entity.Book;
import com.example.lms2.entity.Librarian;
import com.example.lms2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public Book createdBook(Book book){
        return bookRepository.save(book);
    }
    public Book getById(Long id){
        return bookRepository.findById(id).orElse(null);
    }
    public void updateBook(Book book){
        bookRepository.save(book);
    }
    public Page<Book> getAll(Integer pageNo, Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return bookRepository.findAll(paging);}

    public boolean checkBook(Book book){
        Book book1 = bookRepository.findByBookCode(book.getBookCode());
        return (book1==null);
    }
    public List<Book> searchBook(String keyword){
        return bookRepository.search(keyword);
    }

}
