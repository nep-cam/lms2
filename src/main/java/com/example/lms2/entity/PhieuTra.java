package com.example.lms2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "phieutra")
public class PhieuTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ma_phieu_tra")
    private Long id;
    @Column(name="ngay_tra")
    private LocalDateTime createdDate;

    @ManyToOne()
    @JoinColumn(name = "ma_phieu_muon")
    private PhieuMuon phieuMuon;

    @ManyToOne()
    @JoinColumn(name = "nguoi_lap_phieu")
    private Librarian librarian;

    @ManyToMany
    @JoinTable(
            name = "chi_tiet_phieu_tra",
            joinColumns = @JoinColumn(name = "ma_phieu_tra"),
            inverseJoinColumns = @JoinColumn(name = "ma_sach"))
    Set<Book> bookSet;
}
