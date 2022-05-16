package com.example.e_supermarket.Pembeli.ResponseModelPembeli;

import com.example.e_supermarket.Pembeli.Model.BuatPesanan;


import java.util.List;

public class ResponseBuatPesanan {
    private List<BuatPesanan> data;

    public List<BuatPesanan> getData() {
        return data;
    }

    public void setData(List<BuatPesanan> data) {
        this.data = data;
    }
}
