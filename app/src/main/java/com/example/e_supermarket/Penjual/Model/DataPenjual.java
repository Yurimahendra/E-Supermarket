package com.example.e_supermarket.Penjual.Model;

import java.util.Date;

public class DataPenjual {
    int id;
    Long nik ;
    String nama ;
    String jenis_kelamin ;
    String no_ponsel ;
    String tempat_lahir;
    String tanggal_lahir ;
    String alamat ;
    String nama_toko ;

    public DataPenjual() {

    }

    public DataPenjual(int id, Long nik, String nama, String jenis_kelamin, String no_ponsel, String tempat_lahir, String tanggal_lahir, String alamat, String nama_toko) {
        this.id = id;
        this.nik = nik;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.no_ponsel = no_ponsel;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.nama_toko = nama_toko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getNik() {
        return nik;
    }

    public void setNik(Long nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_ponsel() {
        return no_ponsel;
    }

    public void setNo_ponsel(String no_ponsel) {
        this.no_ponsel = no_ponsel;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }
}
