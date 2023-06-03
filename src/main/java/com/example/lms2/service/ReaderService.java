package com.example.lms2.service;


import com.example.lms2.entity.Book;
import com.example.lms2.entity.Reader;
import com.example.lms2.repository.BookRepository;
import com.example.lms2.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    public Reader createdReader(Reader reader){
        return readerRepository.save(reader);
    }
    public Reader getById(Long id){
        return readerRepository.findById(id).orElse(null);
    }
    public void updateBook(Reader reader){
        readerRepository.save(reader);
    }
    public Page<Reader> getAll(Integer pageNo, Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return readerRepository.findAll(paging);}

    public List<Reader> getByIdOrFullName( Long id, String name) {
        return readerRepository.findDistinctReaderByIdOrFullName(id, name);
    }

    public boolean checkReader(Reader reader) {
        return true;
    }
}
