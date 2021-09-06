package com.example.e_supermarket;

public class DataProduk {
    String id;
    String nama_barang ;
    String merk ;
    long harga ;
    long stok ;
    String satuan;
    String gambar;
    String deskripsi;

    public DataProduk() {

    }

    public DataProduk(String id, String nama_barang, String merk, long harga, long stok, String satuan, String gambar, String deskripsi) {
        this.id = id;
        this.nama_barang = nama_barang;
        this.merk = merk;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public long getStok() {
        return stok;
    }

    public void setStok(long stok) {
        this.stok = stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
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
