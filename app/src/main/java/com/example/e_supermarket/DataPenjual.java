package com.example.e_supermarket;

import java.util.Date;

public class DataPenjual {
    String nik ;
    String nama ;
    String jenis_kelamin ;
    String no_ponsel ;
    String tempat_lahir;
    String tanggal_lahir ;
    String alamat ;
    String nama_toko ;

    public DataPenjual(String nik, String nama, String jenis_kelamin, String no_ponsel, String tempat_lahir, String tanggal_lahir, String alamat, String nama_toko) {
        this.nik = nik;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.no_ponsel = no_ponsel;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.nama_toko = nama_toko;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getNo_ponsel() {
        return no_ponsel;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNama_toko() {
        return nama_toko;
    }
}
