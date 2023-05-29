package com.example.lms2.repository;

import com.example.lms2.entity.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaySlipRepository extends JpaRepository<PaySlip, Long> {

}
