package com.example.e_supermarket.Penjual.ResponseModel;

import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.Model.StatusLogin;

import java.util.List;

public class ResponseStatusLogin {
    private List<StatusLogin> status;

    public List<StatusLogin> getStatus() {
        return status;
    }

    public void setStatus(List<StatusLogin> status) {
        this.status = status;
    }
}
