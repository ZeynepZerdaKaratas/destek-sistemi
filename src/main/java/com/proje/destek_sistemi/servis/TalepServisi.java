package com.proje.destek_sistemi.servis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proje.destek_sistemi.depo.TalepDeposu;
import com.proje.destek_sistemi.model.Durum;
import com.proje.destek_sistemi.model.Talep;

@Service
public class TalepServisi {

    private final TalepDeposu depo;
    private final MailServisi mailServisi; // Yeni eklediğimiz servis

    public TalepServisi(TalepDeposu depo, MailServisi mailServisi) {
        this.depo = depo;
        this.mailServisi = mailServisi;
    }

    public List<Talep> tumTalepleriGetir() {
        return depo.findAll();
    }

    public Talep talepKaydet(Talep talep) {
        // 1. Önce veritabanına kaydet
        talep.setDurum(Durum.ACIK);
        Talep kaydedilen = depo.save(talep);

        // 2. PROJE GEREĞİ: Hocaya Bildirim Maili At
        // Hocanın mail adresi: huseyinvural02@gmail.com
        String konu = "Final Projesi - Yeni Talep Var";
        String mesaj = "Hocam Merhaba,\nSisteme yeni bir talep eklendi.\n" +
                       "Başlık: " + talep.getBaslik() + "\n" +
                       "Açıklama: " + talep.getAciklama() + "\n" +
                       "Öğrenci: Zeynep Zerda KARATAŞ";

        mailServisi.mailGonder("huseyinvural02@gmail.com", konu, mesaj);

        // 3. (İsteğe bağlı) Kullanıcı kendi mailini yazdıysa ona da at
        if (talep.getEmail() != null && !talep.getEmail().isEmpty()) {
            mailServisi.mailGonder(talep.getEmail(), "Talebiniz Alındı", "Talebiniz işleme alınmıştır.");
        }

        return kaydedilen;
    }
}