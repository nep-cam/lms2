package com.example.lms2.repository;


import com.example.lms2.entity.LoanSlip;
import com.example.lms2.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    List<Reader> findDistinctReaderByIdOrFullName(Long id, String fullName);
}
