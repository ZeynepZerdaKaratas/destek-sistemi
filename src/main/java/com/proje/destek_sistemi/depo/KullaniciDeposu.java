package com.proje.destek_sistemi.depo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proje.destek_sistemi.model.Kullanici;

public interface KullaniciDeposu extends JpaRepository<Kullanici, Long> {
    // Özel sorgu: Kullanıcı adı ve şifresi uyuşan var mı?
    Optional<Kullanici> findByKullaniciAdiAndSifre(String kullaniciAdi, String sifre);
}