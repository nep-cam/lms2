package com.example.lms2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "sach")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="ma_sach")
    private String bookCode;
    @Column(name = "ten_sach")
    private String nameBook;
    @Column(name="so_luong")
    private int totalNumber;
    @Column(name="so_luong_muon")
    private int remainingNumber;
    @Column(name="tac_gia")
    private String author;
    @Column(name = "the_loai")
    private String category;

}
