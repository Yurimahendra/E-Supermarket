package com.example.e_supermarket.Pembeli.Model;

public class BuatPesanan {
    private String id;
    private String idPesanan;
    private String namaPemesan;
    private String namaBarangPesan;
    private String MerkBarangPesan;
    private int jumlahBarangPesan;
    private String hargaBarangPesan;
    private String TotalHargaPesan;
    private String ongkirPesan;
    private String alamatKirimPesan;
    private String NohpPesan;
    private String tglKirimPesan;
    private String MetodeBayar;
    private String Status;

    public BuatPesanan(String id, String idPesanan, String namaPemesan, String namaBarangPesan, String merkBarangPesan, int jumlahBarangPesan, String hargaBarangPesan, String totalHargaPesan, String ongkirPesan, String alamatKirimPesan, String nohpPesan, String tglKirimPesan, String metodeBayar, String status) {
        this.id = id;
        this.idPesanan = idPesanan;
        this.namaPemesan = namaPemesan;
        this.namaBarangPesan = namaBarangPesan;
        MerkBarangPesan = merkBarangPesan;
        this.jumlahBarangPesan = jumlahBarangPesan;
        this.hargaBarangPesan = hargaBarangPesan;
        TotalHargaPesan = totalHargaPesan;
        this.ongkirPesan = ongkirPesan;
        this.alamatKirimPesan = alamatKirimPesan;
        NohpPesan = nohpPesan;
        this.tglKirimPesan = tglKirimPesan;
        MetodeBayar = metodeBayar;
        Status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getNamaBarangPesan() {
        return namaBarangPesan;
    }

    public void setNamaBarangPesan(String namaBarangPesan) {
        this.namaBarangPesan = namaBarangPesan;
    }

    public String getMerkBarangPesan() {
        return MerkBarangPesan;
    }

    public void setMerkBarangPesan(String merkBarangPesan) {
        MerkBarangPesan = merkBarangPesan;
    }

    public int getJumlahBarangPesan() {
        return jumlahBarangPesan;
    }

    public void setJumlahBarangPesan(int jumlahBarangPesan) {
        this.jumlahBarangPesan = jumlahBarangPesan;
    }

    public String getHargaBarangPesan() {
        return hargaBarangPesan;
    }

    public void setHargaBarangPesan(String hargaBarangPesan) {
        this.hargaBarangPesan = hargaBarangPesan;
    }

    public String getTotalHargaPesan() {
        return TotalHargaPesan;
    }

    public void setTotalHargaPesan(String totalHargaPesan) {
        TotalHargaPesan = totalHargaPesan;
    }

    public String getOngkirPesan() {
        return ongkirPesan;
    }

    public void setOngkirPesan(String ongkirPesan) {
        this.ongkirPesan = ongkirPesan;
    }

    public String getAlamatKirimPesan() {
        return alamatKirimPesan;
    }

    public void setAlamatKirimPesan(String alamatKirimPesan) {
        this.alamatKirimPesan = alamatKirimPesan;
    }

    public String getNohpPesan() {
        return NohpPesan;
    }

    public void setNohpPesan(String nohpPesan) {
        NohpPesan = nohpPesan;
    }

    public String getTglKirimPesan() {
        return tglKirimPesan;
    }

    public void setTglKirimPesan(String tglKirimPesan) {
        this.tglKirimPesan = tglKirimPesan;
    }

    public String getMetodeBayar() {
        return MetodeBayar;
    }

    public void setMetodeBayar(String metodeBayar) {
        MetodeBayar = metodeBayar;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
