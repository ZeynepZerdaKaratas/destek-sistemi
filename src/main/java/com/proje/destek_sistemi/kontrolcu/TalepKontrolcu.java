package com.proje.destek_sistemi.kontrolcu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proje.destek_sistemi.model.Talep;
import com.proje.destek_sistemi.servis.TalepServisi;

@RestController
@RequestMapping("/api/talepler")
@CrossOrigin(origins = "*") // Her yerden gelen isteği kabul et
public class TalepKontrolcu {

    @Autowired
    private TalepServisi servis;

    @GetMapping
    public List<Talep> listele() {
        return servis.tumTalepleriGetir();
    }

    @PostMapping
    public Talep ekle(@RequestBody Talep talep) {
        return servis.talepKaydet(talep);
    }
}