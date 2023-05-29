package com.example.lms2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "docgia")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_doc_gia")
    private Long id;

    @Column(name="ho_ten")
    private String fullName;
    @Column(name="ngay_sinh")
    private LocalDate birthday;
    @Column(name="cccd")
    private String cccd;
    @Column(name = "dia_chi")
    private String address;
    @Column(name = "sdt")
    private String phoneNumber;
    @Column(name = "ngay_lap")
    private LocalDate createdDate;
}
