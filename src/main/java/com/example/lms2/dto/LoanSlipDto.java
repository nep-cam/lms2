package com.example.lms2.dto;

import com.example.lms2.entity.Book;
import com.example.lms2.entity.LoanSlip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class LoanSlipDto {
    private Long id;
    private String librarian;
    private String reader;
    private String createDate;
    private LocalDate dueDate;
    private String books;
    private String status;

    public LoanSlipDto(LoanSlip loanSlip) {
        id = loanSlip.getId();
        librarian = loanSlip.getLibrarian().getFullName();
        reader = loanSlip.getReader().getFullName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        createDate = loanSlip.getCreateDate().format(formatter);
        dueDate = loanSlip.getDueDate();
        status = loanSlip.getStatus();
        books = "";
        for (Book book : loanSlip.getBookSet()) {
            books += "("+book.getBookCode()+")"+book.getNameBook() + ";\n";
        }
    }
}
