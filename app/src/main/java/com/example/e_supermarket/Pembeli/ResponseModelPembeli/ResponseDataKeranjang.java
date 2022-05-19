package com.example.e_supermarket.Pembeli.ResponseModelPembeli;

import com.example.e_supermarket.Pembeli.Model.DataKeranjang;
import com.example.e_supermarket.Penjual.Model.DataProduk;

import java.util.List;

public class ResponseDataKeranjang {
    private List<DataKeranjang> data;

    public List<DataKeranjang> getData() {
        return data;
    }

    public void setData(List<DataKeranjang> data) {
        this.data = data;
    }
}
