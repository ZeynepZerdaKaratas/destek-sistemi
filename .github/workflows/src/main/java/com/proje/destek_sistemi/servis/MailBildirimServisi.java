package com.proje.destek_sistemi.servis;

import org.springframework.stereotype.Service;

// Interface'i burada implemente ediyoruz (Polimorfizm)
@Service
public class MailBildirimServisi implements IBildirimServisi {

    private final MailServisi mailServisi; // Python ile konuşan postacımız

    public MailBildirimServisi(MailServisi mailServisi) {
        this.mailServisi = mailServisi;
    }

    @Override
    public void bildirimGonder(String mesaj) {
        // HOCANIN MAİL ADRESİ BURAYA SABİTLENDİ
        // Sisteme bir bildirim geldiğinde direkt hocaya mail atacak.
        String hocaMail = "huseyinvural02@gmail.com"; 
        String baslik = "🔔 Sistem Bildirimi (Final Projesi)";
        
        // Postacıyı çağırıyoruz
        mailServisi.mailGonder(hocaMail, baslik, mesaj);
        
        System.out.println("✅ [MailBildirimServisi] Hocaya bildirim tetiklendi.");
    }
}