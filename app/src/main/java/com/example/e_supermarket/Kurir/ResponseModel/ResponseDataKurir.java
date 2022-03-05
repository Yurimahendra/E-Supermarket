package com.example.e_supermarket.Kurir.ResponseModel;

import com.example.e_supermarket.Kurir.Model.DataKurir;
import java.util.List;

public class ResponseDataKurir {
    private int kode;
    private String pesan;
    private List<DataKurir> data;

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

    public List<DataKurir> getDataKurir() {
        return data;
    }

    public void setDataKurir(List<DataKurir> dataKurir) {
        this.data = dataKurir;
    }
}
