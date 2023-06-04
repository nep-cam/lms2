package com.example.lms2.dto;

import com.example.lms2.entity.Book;
import com.example.lms2.entity.PaySlip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaySlipDto {
    private Long id;

    private Long idLoanSlip;
    private String createDate;
    private String books;


    public PaySlipDto(PaySlip paySlip) {
        id = paySlip.getId();

        idLoanSlip = paySlip.getLoanSlip().getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        createDate = paySlip.getCreatedDate().format(formatter);
        books = "";
        for (Book book : paySlip.getBookSet()) {
            books += "("+book.getBookCode()+")"+book.getNameBook() + ";\n";
        }
    }
}
