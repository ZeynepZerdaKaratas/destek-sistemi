package com.proje.destek_sistemi.servis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailServisi {

    private final WebClient webClient;

    public MailServisi(WebClient.Builder webClientBuilder) {
        // Python servisinin adresi buraya sabitlendi
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
    }

    public void mailGonder(String kime, String baslik, String icerik) {
        // Veriyi Map olarak hazırlıyoruz (Hata riskini sıfırlar)
        Map<String, String> mailVerisi = new HashMap<>();
        mailVerisi.put("email", kime);
        mailVerisi.put("baslik", baslik);
        mailVerisi.put("mesaj", icerik);

        try {
            webClient.post()
                .uri("/send-mail")
                .bodyValue(mailVerisi)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                    sonuc -> System.out.println("✅ Mail Başarılı: " + kime),
                    hata -> System.err.println("❌ Mail Hatası: " + hata.getMessage())
                );
        } catch (Exception e) {
            System.err.println("Bağlantı Hatası: " + e.getMessage());
        }
    }
}