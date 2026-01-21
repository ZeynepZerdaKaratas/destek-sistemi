package com.proje.destek_sistemi.kontrolcu;

import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proje.destek_sistemi.depo.KullaniciDeposu;
import com.proje.destek_sistemi.model.Kullanici;

@RestController
@RequestMapping("/api/kullanicilar")
@CrossOrigin(origins = "*") // <--- DİKKAT: Burayı yıldız yaptık, herkes erişebilsin.
public class KullaniciKontrolcu {

    private final KullaniciDeposu depo;

    public KullaniciKontrolcu(KullaniciDeposu depo) {
        this.depo = depo;
    }

    @PostMapping("/kayit")
    public Kullanici kayitOl(@RequestBody Kullanici kullanici) {
        System.out.println("GELEN KAYIT İSTEĞİ: " + kullanici.getKullaniciAdi()); // Konsola yazdıralım
        return depo.save(kullanici);
    }

    @PostMapping("/giris")
    public Kullanici girisYap(@RequestBody Kullanici girisBilgisi) {
        Optional<Kullanici> kullanici = depo.findByKullaniciAdiAndSifre(
            girisBilgisi.getKullaniciAdi(), 
            girisBilgisi.getSifre()
        );
        if (kullanici.isPresent()) {
            return kullanici.get();
        } else {
            throw new RuntimeException("Hatalı Giriş");
        }
    }
}