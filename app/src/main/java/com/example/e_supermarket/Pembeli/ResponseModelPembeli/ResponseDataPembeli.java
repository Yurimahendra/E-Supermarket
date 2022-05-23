package com.example.e_supermarket.Pembeli.ResponseModelPembeli;

import com.example.e_supermarket.Pembeli.Model.DataPembeli;

import java.util.List;

public class ResponseDataPembeli {
    //private int kode;
    //private String pesan;
    private List<DataPembeli> data;
    private int id;
    private long nik ;
    private String nama ;
    private String jenis_kelamin ;
    private String no_ponsel ;
    private String tempat_lahir;
    private String tanggal_lahir ;
    private String alamat ;
    private String gambar;

    public List<DataPembeli> getDataPembeli() {
        return data;
    }

    public void setDataPembeli(List<DataPembeli> dataPembeli) {
        this.data = dataPembeli;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNik() {
        return nik;
    }

    public void setNik(long nik) {
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
