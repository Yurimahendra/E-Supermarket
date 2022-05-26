package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Penjual.Activity.HalamanProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarPesananPembeliActivity extends AppCompatActivity {
    private int index;
    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private String nama ;
    private String nama_bank ;
    private long no_rekening ;

    EditText NamaBayar;
    EditText Namabankpenjual;
    EditText NoRek;
    ImageView BackBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_pesanan_pembeli);
        NamaBayar = findViewById(R.id.EdtNamaBayar);
        Namabankpenjual = findViewById(R.id.EdtNaBankBayar);
        NoRek = findViewById(R.id.EdtNo_RekBayar);
        BackBayar = findViewById(R.id.imgBackbayar);
        BackBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePenjual();
    }

    private void getProfilePenjual() {
        //pbDataPenjual.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()){
                    try {
                        dataPenjualList = response.body().getDataPenjual();

                        nama = dataPenjualList.get(index).getNama();
                        nama_bank = dataPenjualList.get(index).getNama_bank();
                        no_rekening = dataPenjualList.get(index).getNo_rekening();

                        NamaBayar.setText(nama);
                        Namabankpenjual.setText(nama_bank);
                        NoRek.setText(""+no_rekening);

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                //pbDataPenjual.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(BayarPesananPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbDataPenjual.setVisibility(View.GONE);
            }
        });

    }
}