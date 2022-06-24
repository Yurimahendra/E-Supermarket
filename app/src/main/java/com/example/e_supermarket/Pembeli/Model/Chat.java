package com.example.e_supermarket.Pembeli.Model;

public class Chat {
    private String isiPesan;

    private String nama_pembeli;
    private String no_pembeli;
    private String foto_pembeli;

    private String nama_penjual;
    private String no_penjual;
    private String foto_penjual;

    private String tanggal;

    public Chat() {
    }

    public Chat(String isiPesan, String nama_pembeli, String no_pembeli, String foto_pembeli, String nama_penjual, String no_penjual, String foto_penjual, String tanggal) {
        this.isiPesan = isiPesan;
        this.nama_pembeli = nama_pembeli;
        this.no_pembeli = no_pembeli;
        this.foto_pembeli = foto_pembeli;
        this.nama_penjual = nama_penjual;
        this.no_penjual = no_penjual;
        this.foto_penjual = foto_penjual;
        this.tanggal = tanggal;
    }

    public String getIsiPesan() {
        return isiPesan;
    }

    public void setIsiPesan(String isiPesan) {
        this.isiPesan = isiPesan;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getNo_pembeli() {
        return no_pembeli;
    }

    public void setNo_pembeli(String no_pembeli) {
        this.no_pembeli = no_pembeli;
    }

    public String getFoto_pembeli() {
        return foto_pembeli;
    }

    public void setFoto_pembeli(String foto_pembeli) {
        this.foto_pembeli = foto_pembeli;
    }

    public String getNama_penjual() {
        return nama_penjual;
    }

    public void setNama_penjual(String nama_penjual) {
        this.nama_penjual = nama_penjual;
    }

    public String getNo_penjual() {
        return no_penjual;
    }

    public void setNo_penjual(String no_penjual) {
        this.no_penjual = no_penjual;
    }

    public String getFoto_penjual() {
        return foto_penjual;
    }

    public void setFoto_penjual(String foto_penjual) {
        this.foto_penjual = foto_penjual;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
