package com.example.lms2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLoanSlip {
    private String librarianName;
    private Long readerId;
    private LocalDate createDate;
    private LocalDate dueDate;
    private List<String> bookCodes;
}
