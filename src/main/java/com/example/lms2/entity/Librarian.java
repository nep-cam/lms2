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
@Table
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_thu_thu")
    private Long id;
    @Column(name="ho_ten")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name="address")
    private String address;
    @Column(name = "ten_dang_nhap")
    private String username;
    @Column(name="mat_khau")
    private String password;
}
