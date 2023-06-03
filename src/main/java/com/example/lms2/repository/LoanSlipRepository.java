package com.example.lms2.repository;


import com.example.lms2.entity.LoanSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanSlipRepository extends JpaRepository<LoanSlip, Long> {


}
