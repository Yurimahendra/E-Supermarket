package com.example.e_supermarket;

public class DataProduk {
    //String id;
    String nama_barang ;
    String merk ;
    String harga ;
    String stok ;
    String satuan;
    String deskripsi;

    public DataProduk(String nama_barang, String merk, String harga, String stok, String satuan, String deskripsi) {
        this.nama_barang = nama_barang;
        this.merk = merk;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
        this.deskripsi = deskripsi;
    }



    /** public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }**/


    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public void setSatuan(String satuan) {
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
