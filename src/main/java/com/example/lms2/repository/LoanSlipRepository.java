package com.example.lms2.repository;


import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanSlipRepository extends JpaRepository<LoanSlip, Long> {
    List<LoanSlip> findLoanSlipByIdOrReaderOrderByCreateDate(Long id, Reader reader);

}
