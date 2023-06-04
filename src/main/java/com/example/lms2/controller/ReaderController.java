package com.example.lms2.controller;

import com.example.lms2.entity.Reader;
import com.example.lms2.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReaderController {
    @Autowired
    ReaderService readerService;

    @PostMapping("home/reader/find-reader")
    public ModelAndView searchReader(@RequestParam(value = "idReader", required = false) Long id,
                                     @RequestParam(value = "nameReader" , required = false) String name,
                                     @RequestParam("username") String username) {

        ModelAndView modelAndView = new ModelAndView( "reader-view/find-reader");
        List<Reader> readers = readerService.getByIdOrFullName(id, name);
        modelAndView.addObject("readers", readers);
        modelAndView.addObject("idReader", id);
        modelAndView.addObject("nameReader", name);
        modelAndView.addObject("username", username);

        return modelAndView;
    }
    @PostMapping("home/reader/add-reader")
    public ModelAndView addReader(@RequestParam("username") String username,
                                  @ModelAttribute() Reader reader) {
        ModelAndView modelAndView = new ModelAndView("reader-view/add-reader");
        if (readerService.checkReader(reader)) {
            reader.setCreatedDate(LocalDate.now());
            readerService.createdReader(reader);
            modelAndView.addObject("successMsg", "Success!");
        } else {
            modelAndView.addObject("errorMsg", "Fail!");
        }
        modelAndView.addObject("username", username);
        return modelAndView;
    }
}
