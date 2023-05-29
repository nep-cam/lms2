package com.example.lms2.service;

import com.example.lms2.entity.PaySlip;
import com.example.lms2.repository.PaySlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaySlipService {
    @Autowired
    private PaySlipRepository paySlipRepository;

    public PaySlip createdPaySlip(PaySlip paySlip) {
        return paySlipRepository.save(paySlip);
    }

    public PaySlip getById(Long id) {
        return paySlipRepository.findById(id).orElse(null);
    }

    public void updatePaySlip(PaySlip paySlip) {
        paySlipRepository.save(paySlip);
    }

    public Page<PaySlip> getAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return paySlipRepository.findAll(paging);
    }


}
