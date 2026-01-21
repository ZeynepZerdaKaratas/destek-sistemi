package com.proje.destek_sistemi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "kullanicilar")
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kullaniciAdi; // Giriş için
    private String sifre;        // Şifre
    private String rol;          // "PERSONEL" veya "YONETICI"
    private String email;        // İletişim için
}