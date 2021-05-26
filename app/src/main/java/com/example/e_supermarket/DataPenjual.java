package com.example.e_supermarket;

public class DataPenjual {
    String nik ;
    String nama ;
    String jk ;
    String nopons ;
    String tela;
    String tala ;
    String alamat ;
    String nato ;

    public DataPenjual(String nik, String nama, String jk, String nopons, String tela, String tala, String alamat, String nato) {
        this.nik = nik;
        this.nama = nama;
        this.jk = jk;
        this.nopons = nopons;
        this.tela = tela;
        this.tala = tala;
        this.alamat = alamat;
        this.nato = nato;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getJk() {
        return jk;
    }

    public String getNopons() {
        return nopons;
    }

    public String getTela() {
        return tela;
    }

    public String getTala() {
        return tala;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNato() {
        return nato;
    }
}
