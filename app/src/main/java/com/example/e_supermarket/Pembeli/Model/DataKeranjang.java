package com.example.e_supermarket.Pembeli.Model;

public class DataKeranjang {
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
    int stok ;
    //@SerializedName("gambar")
    String gambar;
    //@SerializedName("deskripsi")
    String deskripsi;

    public DataKeranjang(int id, String nama_barang, String merk, String harga, String satuan, int stok, String gambar, String deskripsi) {
        this.id = id;
        this.nama_barang = nama_barang;
        this.merk = merk;
        this.harga = harga;
        this.satuan = satuan;
        this.stok = stok;
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
