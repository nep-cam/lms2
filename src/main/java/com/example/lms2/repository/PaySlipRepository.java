package com.example.lms2.repository;

import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaySlipRepository extends JpaRepository<PaySlip, Long> {
    List<PaySlip> findByLoanSlip(LoanSlip loanSlip);
    List<PaySlip> findByIdOrLoanSlip(Long id, LoanSlip loanSlip);
}
