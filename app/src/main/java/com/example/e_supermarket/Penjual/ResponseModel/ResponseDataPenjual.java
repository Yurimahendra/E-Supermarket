package com.example.e_supermarket.Penjual.ResponseModel;

import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Model.DataPenjual;

import java.util.List;

public class ResponseDataPenjual {
    private int kode;
    private String pesan;

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

    public List<DataPenjual> getDataPenjual() {
        return dataPenjual;
    }

    public void setDataPenjual(List<DataPenjual> dataPenjual) {
        this.dataPenjual = dataPenjual;
    }

    private List<DataPenjual> dataPenjual;
}
