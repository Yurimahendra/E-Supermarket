package com.example.e_supermarket.Pembeli.ResponseModelPembeli;

import com.example.e_supermarket.Pembeli.Model.DataPembeli;

import java.util.List;

public class ResponseDataPembeli {
    private int kode;
    private String pesan;
    private List<DataPembeli> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<DataPembeli> getDataPembeli() {
        return data;
    }

    public void setDataPembeli(List<DataPembeli> dataPembeli) {
        this.data = dataPembeli;
    }
}
