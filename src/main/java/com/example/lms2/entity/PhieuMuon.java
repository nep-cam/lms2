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
@Table(name = "phieumuon")
public class PhieuMuon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phieu_muon")
    private Long id;
    @Column(name="ngay_muon")
    private LocalDateTime createdDate;
    @Column(name = "han_muon")
    private LocalDateTime dueDate;
    @Column(name = "trang_thai")
    private String status;

    @ManyToOne()
    @JoinColumn(name="nguoi_muon")
    private Reader reader;
    @ManyToOne()
    @JoinColumn(name="nguoi_lap_phieu")
    private Librarian librarian;
    @ManyToMany
    @JoinTable(
            name = "chi_tiet_phieu_muon",
            joinColumns = @JoinColumn(name = "ma_phieu_muon"),
            inverseJoinColumns = @JoinColumn(name = "ma_sach"))
    Set<Book> bookSet;
}
