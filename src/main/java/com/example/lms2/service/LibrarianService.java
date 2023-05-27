package com.example.lms2.service;

import com.example.lms2.entity.Librarian;
import com.example.lms2.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;
    public boolean isUser(String username, String password){
        Librarian user = librarianRepository.findByUsername(username);
        if(user!=null && user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
