package com.example.lms2.repository;

import com.example.lms2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByNameBook(String nameBook);
    Book findByBookCode(String bookCode);

    @Query("SELECT b FROM Book b WHERE b.nameBook LIKE %?1%" + " OR b.author LIKE %?1%" + " OR b.category LIKE %?1%")
    public List<Book> search(String keyword);
}
