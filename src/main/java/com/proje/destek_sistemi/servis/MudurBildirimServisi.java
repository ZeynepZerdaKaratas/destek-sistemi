package com.proje.destek_sistemi.servis;

import org.springframework.stereotype.Service;

@Service
public class MudurBildirimServisi implements IBildirimServisi {

    private final MailServisi mailServisi; // Python'a ulaşan postacımız

    // Constructor Injection (Postacıyı işe alıyoruz)
    public MudurBildirimServisi(MailServisi mailServisi) {
        this.mailServisi = mailServisi;
    }
    
    @Override
    public void bildirimGonder(String mesaj) {
        // ARTIK SİMÜLASYON YOK, GERÇEK MAİL VAR! 🚀
        
        // Hocanın istediği adres:
        String hedefMail = "huseyinvural02@gmail.com"; 
        String mailBasligi = "🚨 KRİTİK BİLDİRİM - Destek Sistemi";
        
        System.out.println("📨 [MudurBildirimServisi] Hocaya mail gönderiliyor...");

        // Python servisine sinyal çakıyoruz
        mailServisi.mailGonder(hedefMail, mailBasligi, mesaj);
    }
}