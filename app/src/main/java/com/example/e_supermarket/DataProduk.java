package com.example.e_supermarket;

public class DataProduk {
    String id;
    String nama_barang ;
    String merk ;
    Long harga ;
    Long stok ;
    String satuan;
    String deskripsi;

    public DataProduk(String id, String nama_barang, String merk, Long harga, Long stok, String satuan, String deskripsi) {
        this.id = id;
        this.nama_barang = nama_barang;
        this.merk = merk;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
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

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public Long getStok() {
        return stok;
    }

    public void setStok(Long stok) {
        this.stok = stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
