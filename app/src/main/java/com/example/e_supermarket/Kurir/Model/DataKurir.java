package com.example.e_supermarket.Kurir.Model;

public class DataKurir {

    long nikK ;
    String namaK ;
    String jenis_kelaminK ;
    String no_ponselK ;
    String tempat_lahirK;
    String tanggal_lahirK ;
    String alamatK ;

    public DataKurir(long nikK, String namaK, String jenis_kelaminK, String no_ponselK, String tempat_lahirK, String tanggal_lahirK, String alamatK) {
        this.nikK = nikK;
        this.namaK = namaK;
        this.jenis_kelaminK = jenis_kelaminK;
        this.no_ponselK = no_ponselK;
        this.tempat_lahirK = tempat_lahirK;
        this.tanggal_lahirK = tanggal_lahirK;
        this.alamatK = alamatK;
    }

    public long getNikK() {
        return nikK;
    }

    public void setNikK(long nikK) {
        this.nikK = nikK;
    }

    public String getNamaK() {
        return namaK;
    }

    public void setNamaK(String namaK) {
        this.namaK = namaK;
    }

    public String getJenis_kelaminK() {
        return jenis_kelaminK;
    }

    public void setJenis_kelaminK(String jenis_kelaminK) {
        this.jenis_kelaminK = jenis_kelaminK;
    }

    public String getNo_ponselK() {
        return no_ponselK;
    }

    public void setNo_ponselK(String no_ponselK) {
        this.no_ponselK = no_ponselK;
    }

    public String getTempat_lahirK() {
        return tempat_lahirK;
    }

    public void setTempat_lahirK(String tempat_lahirK) {
        this.tempat_lahirK = tempat_lahirK;
    }

    public String getTanggal_lahirK() {
        return tanggal_lahirK;
    }

    public void setTanggal_lahirK(String tanggal_lahirK) {
        this.tanggal_lahirK = tanggal_lahirK;
    }

    public String getAlamatK() {
        return alamatK;
    }

    public void setAlamatK(String alamatK) {
        this.alamatK = alamatK;
    }
}
