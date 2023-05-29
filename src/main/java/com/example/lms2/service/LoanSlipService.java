package com.example.lms2.service;

import com.example.lms2.dto.CreateLoanSlip;
import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.Reader;
import com.example.lms2.repository.LoanSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LoanSlipService {
    @Autowired
    private LoanSlipRepository loanSlipRepository;

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

//    public LoanSlip createdLoanSlip(CreateLoanSlip createLoanSlip){}
}
