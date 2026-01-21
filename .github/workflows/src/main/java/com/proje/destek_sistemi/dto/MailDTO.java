package com.proje.destek_sistemi.dto; 

import lombok.Data;

@Data
public class MailDTO {
    private String kime;
    private String baslik;
    private String icerik;
}