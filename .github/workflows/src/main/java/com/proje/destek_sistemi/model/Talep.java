package com.proje.destek_sistemi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "talepler")
public class Talep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    
    
    private String email; 

    @Column(length = 1000)
    private String aciklama;

    @Enumerated(EnumType.STRING)
    private Durum durum = Durum.ACIK;
    
    
    private String oncelik = "NORMAL"; 

    private LocalDateTime olusturmaTarihi = LocalDateTime.now();
}