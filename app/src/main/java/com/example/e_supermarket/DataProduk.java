package com.example.e_supermarket;

public class DataProduk {
    String nama_barang ;
    String merk ;
    String harga ;
    String stok ;
    String satuan;

    public DataProduk(String nama_barang, String merk, String harga, String stok, String satuan) {
        this.nama_barang = nama_barang;
        this.merk = merk;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getMerk() {
        return merk;
    }

    public String getHarga() {
        return harga;
    }

    public String getStok() {
        return stok;
    }

    public String getSatuan() {
        return satuan;
    }
}
