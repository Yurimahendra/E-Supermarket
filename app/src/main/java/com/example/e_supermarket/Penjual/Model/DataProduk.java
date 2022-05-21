package com.example.e_supermarket.Penjual.Model;

import com.google.gson.annotations.SerializedName;

public class DataProduk {
    //@SerializedName("id")
    int id;
    //@SerializedName("nama_barang")
    String nama_barang ;
    //@SerializedName("merk")
    String merk ;
    //@SerializedName("harga")
    String harga ;
    //@SerializedName("satuan")
    String satuan;
    //@SerializedName("stok")
    int min_belanja ;
    //@SerializedName("gambar")
    String gambar;
    //@SerializedName("deskripsi")
    String deskripsi;

    public DataProduk(int id, String nama_barang, String merk, String harga, String satuan, int min_belanja, String gambar, String deskripsi) {
        this.id = id;
        this.nama_barang = nama_barang;
        this.merk = merk;
        this.harga = harga;
        this.satuan = satuan;
        this.min_belanja = min_belanja;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getMin_belanja() {
        return min_belanja;
    }

    public void setMin_belanja(int min_belanja) {
        this.min_belanja = min_belanja;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
