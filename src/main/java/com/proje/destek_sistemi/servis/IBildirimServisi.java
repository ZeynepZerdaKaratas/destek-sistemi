package com.proje.destek_sistemi.servis;

// Bildirim sistemleri için Interface yapısını kullandım.
// İleride SMS veya Slack bildirimi eklenirse bu yapıyı bozmadan eklenebilir. (Loose Coupling)
public interface IBildirimServisi {
    void bildirimGonder(String mesaj);
}