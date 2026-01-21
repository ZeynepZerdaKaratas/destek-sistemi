package com.proje.destek_sistemi.depo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proje.destek_sistemi.model.Durum;
import com.proje.destek_sistemi.model.Talep;

@Repository
public interface TalepDeposu extends JpaRepository<Talep, Long> {
    // Özel sorgu: Başlık ve duruma göre sayım
    long countByBaslikAndDurum(String baslik, Durum durum);
}