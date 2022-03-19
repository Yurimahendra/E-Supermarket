package com.example.e_supermarket.Penjual.ResponseModel;

import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Model.DataPenjual;

import java.util.List;

public class ResponseDataPenjual {
    private int kode;
    private String pesan;
    private List<DataPenjual> data;
    private String no_ponsel;

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
        return data;
    }

    public void setDataPenjual(List<DataPenjual> dataPenjual) {
        this.data = dataPenjual;
    }

    public String getNo_ponsel() {
        return no_ponsel;
    }

    public void setNo_ponsel(String no_ponsel) {
        this.no_ponsel = no_ponsel;
    }
}
