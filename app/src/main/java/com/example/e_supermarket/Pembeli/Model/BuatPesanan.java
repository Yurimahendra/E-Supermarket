package com.example.e_supermarket.Pembeli.Model;

public class BuatPesanan {
    private int id;
    private String id_pesanan;
    private String nama;
    private String no_hp;
    private String alamat;
    private String nama_barang;
    private String merk_barang;
    private String harga_barang;
    private int jumlah_pesanan;
    private String satuan;
    private String gambar;
    private String tanggal_pengiriman;
    private String ongkir;
    private String total_harga;
    private String metode_pembayaran;
    private String status;
    private String status_pesanan;
    private String bukti_transfer;

    public BuatPesanan(int id, String id_pesanan, String nama, String no_hp, String alamat, String nama_barang, String merk_barang, String harga_barang, int jumlah_pesanan, String satuan, String gambar, String tanggal_pengiriman, String ongkir, String total_harga, String metode_pembayaran, String status, String status_pesanan, String bukti_transfer) {
        this.id = id;
        this.id_pesanan = id_pesanan;
        this.nama = nama;
        this.no_hp = no_hp;
        this.alamat = alamat;
        this.nama_barang = nama_barang;
        this.merk_barang = merk_barang;
        this.harga_barang = harga_barang;
        this.jumlah_pesanan = jumlah_pesanan;
        this.satuan = satuan;
        this.gambar = gambar;
        this.tanggal_pengiriman = tanggal_pengiriman;
        this.ongkir = ongkir;
        this.total_harga = total_harga;
        this.metode_pembayaran = metode_pembayaran;
        this.status = status;
        this.status_pesanan = status_pesanan;
        this.bukti_transfer = bukti_transfer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getMerk_barang() {
        return merk_barang;
    }

    public void setMerk_barang(String merk_barang) {
        this.merk_barang = merk_barang;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(String harga_barang) {
        this.harga_barang = harga_barang;
    }

    public int getJumlah_pesanan() {
        return jumlah_pesanan;
    }

    public void setJumlah_pesanan(int jumlah_pesanan) {
        this.jumlah_pesanan = jumlah_pesanan;
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

    public String getTanggal_pengiriman() {
        return tanggal_pengiriman;
    }

    public void setTanggal_pengiriman(String tanggal_pengiriman) {
        this.tanggal_pengiriman = tanggal_pengiriman;
    }

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_pesanan() {
        return status_pesanan;
    }

    public void setStatus_pesanan(String status_pesanan) {
        this.status_pesanan = status_pesanan;
    }

    public String getBukti_transfer() {
        return bukti_transfer;
    }

    public void setBukti_transfer(String bukti_transfer) {
        this.bukti_transfer = bukti_transfer;
    }
}
