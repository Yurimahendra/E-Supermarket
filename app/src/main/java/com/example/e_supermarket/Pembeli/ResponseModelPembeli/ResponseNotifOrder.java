package com.example.e_supermarket.Pembeli.ResponseModelPembeli;

import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.Model.NotifOrder;

import java.util.List;

public class ResponseNotifOrder {
    //private List<NotifOrder> data;
    private String title ;
    private String body;

    //public List<NotifOrder> getData() {
      //  return data;
    //}

    //public void setData(List<NotifOrder> data) {
      //  this.data = data;
    //}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
