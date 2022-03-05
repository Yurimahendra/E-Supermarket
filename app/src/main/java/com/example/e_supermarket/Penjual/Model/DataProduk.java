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
    int harga ;
    //@SerializedName("satuan")
    String satuan;
    //@SerializedName("stok")
    int stok ;
    //@SerializedName("gambar")
    String gambar;
    //@SerializedName("deskripsi")
    String deskripsi;

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

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
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
