package com.chairul.pariwisatayogyakarta.Model;

import java.util.List;

public class Tourism {

    private String nama;
    private String alamat;
    private String detail;
    private String gambar;

    public Tourism() {
    }

    public Tourism(String nama, String alamat, String detail, String gambar) {
        this.nama = nama;
        this.alamat = alamat;
        this.detail = detail;
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getDetail() {
        return detail;
    }

    public String getGambar() {
        return gambar;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

