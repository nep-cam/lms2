package com.example.lms2.service;

import com.example.lms2.dto.CreateLoanSlip;
import com.example.lms2.dto.LoanSlipDto;
import com.example.lms2.entity.Book;
import com.example.lms2.entity.Librarian;
import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.Reader;
import com.example.lms2.repository.LibrarianRepository;
import com.example.lms2.repository.LoanSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoanSlipService {
    @Autowired
    private LoanSlipRepository loanSlipRepository;
    @Autowired
    private LibrarianRepository librarianRepository;

    public LoanSlip createdLoanSlip(LoanSlip loanSlip) {
        return loanSlipRepository.save(loanSlip);
    }

    public LoanSlip getById(Long id) {
        return loanSlipRepository.findById(id).orElse(null);
    }

    public void updateLoanSlip(LoanSlip loanSlip) {
        loanSlipRepository.save(loanSlip);
    }

    public Page<LoanSlip> getAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return loanSlipRepository.findAll(paging);
    }
    public List<LoanSlip> getByIdOrReader(Long id, Reader reader){
        return loanSlipRepository.findLoanSlipByIdOrReaderOrderByCreateDate(id,  reader);
    }


}
