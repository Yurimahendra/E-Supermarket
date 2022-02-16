package com.example.e_supermarket.Penjual.ResponseModel;

import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataProduk {

    private int kode;
    private String pesan;
    private List<DataProduk> data;

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

    public List<DataProduk> getData() {
        return data;
    }

    public void setData(List<DataProduk> data) {
        this.data = data;
    }
}
