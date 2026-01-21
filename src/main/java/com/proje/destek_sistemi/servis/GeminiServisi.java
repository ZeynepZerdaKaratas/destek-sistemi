package com.proje.destek_sistemi.servis;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; // Türkçe için şart

@Service
@SuppressWarnings("all")
public class GeminiServisi {

    private final String apiKey = "AIzaSyDtLj2szj_sIErKOBdpOR72CXopBG1OeyE";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GeminiServisi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    public String yapayZekayaSor(String baslik, String aciklama) {
        // Model: gemini-pro
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        String istekMetni = "Sen bir IT destek uzmanısın. Şu sorunu analiz et: Başlık: " + baslik + ", Detay: " + aciklama + 
                            ". Bu sorun ne kadar ACİL? (Cevabın içinde mutlaka 'Normal', 'Yüksek' veya 'Acil' kelimelerinden biri geçsin) ve kısaca tek cümlelik teknik çözüm önerisi ver.";

        Map<String, Object> body = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(Map.of("text", istekMetni)))
            )
        );

        try {
            String jsonCevap = webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            JsonNode root = objectMapper.readTree(jsonCevap);
            return root.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();

        } catch (IOException | RuntimeException e) {
            System.out.println("⚠️ Google Hata Verdi (Yedek Sistem Devrede): " + e.getMessage());
            return yedekCevapUret(aciklama);
        }
    }

    // YEDEK FONKSİYON (TÜRKÇE DESTEKLİ)
    private String yedekCevapUret(String aciklama) {
        // BURASI ÇOK ÖNEMLİ: İngilizce değil, TÜRKÇE kurallarına göre küçültüyoruz.
        // Böylece "YANİYOR", "YANIYOR", "yanıyor" hepsi aynı anlaşılır.
        String kucukAciklama = aciklama.toLowerCase(Locale.forLanguageTag("tr"));
        
        
        System.out.println(" İNCELENEN METİN: " + kucukAciklama);

        String oncelik = "Normal";
        String cozum = "Teknik ekip inceleme yapacak.";

        // Kelime havuzunu genişlettik
        if (kucukAciklama.contains("yangın") || 
            kucukAciklama.contains("duman") || 
            kucukAciklama.contains("acil") || 
            kucukAciklama.contains("patla") ||  // patladı, patlama, patlıyor hepsini yakalar
            kucukAciklama.contains("yanıyor") || 
            kucukAciklama.contains("alev") ||
            kucukAciklama.contains("kıvılcım")) {
            
            oncelik = "ACİL";
            cozum = "Derhal binayı tahliye edin ve itfaiyeyi (110) arayın.";
            
        } else if (kucukAciklama.contains("arızalı") || 
                   kucukAciklama.contains("bozuk") || 
                   kucukAciklama.contains("açılmıyor") || 
                   kucukAciklama.contains("çalışmıyor") || 
                   kucukAciklama.contains("hata")) {
            
            oncelik = "Yüksek";
            cozum = "Cihazın fişini çekin, teknik servis yönlendiriliyor.";
        }

        System.out.println("✅ KARAR VERİLDİ: " + oncelik);
        return "Durum: " + oncelik + ". " + cozum + " (Otomatik Analiz)";
    }
}