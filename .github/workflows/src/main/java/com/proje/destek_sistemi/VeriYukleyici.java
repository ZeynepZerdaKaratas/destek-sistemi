package com.proje.destek_sistemi;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.proje.destek_sistemi.depo.TalepDeposu;
import com.proje.destek_sistemi.model.Durum;
import com.proje.destek_sistemi.model.Talep;

@Component
public class VeriYukleyici implements CommandLineRunner {

    private final TalepDeposu depo;

    public VeriYukleyici(TalepDeposu depo) {
        this.depo = depo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sadece veritabanı boşsa çalışır
        if (depo.count() == 0) {
            
            // --- 1. GRUP: NORMAL TALEPLER ---
            Talep t1 = new Talep();
            t1.setBaslik("Klavye İsteği");
            t1.setAciklama("Ofisteki klavye eskidi, yenisini talep ediyorum.");
            t1.setOncelik("NORMAL"); // Artık String olarak gönderiyoruz
            t1.setDurum(Durum.ACIK);
            t1.setEmail("ornek1@sirket.com"); // Mail ekledik

            Talep t2 = new Talep();
            t2.setBaslik("Şifre Sıfırlama");
            t2.setAciklama("Outlook şifremi unuttum.");
            t2.setOncelik("NORMAL");
            t2.setDurum(Durum.ACIK);
            t2.setEmail("ornek2@sirket.com");

            // --- 2. GRUP: YÜKSEK TALEPLER ---
            Talep t5 = new Talep();
            t5.setBaslik("Yazıcı Hata Veriyor");
            t5.setAciklama("Muhasebe katındaki yazıcı çalışmıyor.");
            t5.setOncelik("YUKSEK");
            t5.setDurum(Durum.ACIK);
            t5.setEmail("muhasebe@sirket.com");

            Talep t6 = new Talep();
            t6.setBaslik("Klima Arızası");
            t6.setAciklama("Toplantı odası çok sıcak.");
            t6.setOncelik("YUKSEK");
            t6.setDurum(Durum.ACIK);
            t6.setEmail("ik@sirket.com");

            // --- 3. GRUP: ACİL TALEPLER ---
            Talep t9 = new Talep();
            t9.setBaslik("Sunucu Odası Isındı");
            t9.setAciklama("Klimalar yetersiz, sunucular kapanabilir!");
            t9.setOncelik("ACIL");
            t9.setDurum(Durum.ACIK);
            t9.setEmail("it@sirket.com");

            Talep t10 = new Talep();
            t10.setBaslik("Sistem Çöktü");
            t10.setAciklama("Müşteriler sisteme giremiyor, acil müdahale.");
            t10.setOncelik("ACIL");
            t10.setDurum(Durum.ACIK);
            t10.setEmail("yonetim@sirket.com");

            // Kaydet
            depo.saveAll(List.of(t1, t2, t5, t6, t9, t10));
            
            System.out.println("✅ [SİSTEM BAŞLATILDI] Örnek Veriler Yüklendi.");
        }
    }
}