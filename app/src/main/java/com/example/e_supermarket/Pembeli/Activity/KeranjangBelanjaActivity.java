package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.Pembeli.Adapter.AdapterItemPembeli;
import com.example.e_supermarket.Pembeli.Adapter.AdapterKeranjangBelanja;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataKeranjang;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataKeranjang;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangBelanjaActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ImageView backKeranjang;

    private SwipeRefreshLayout srlDataKeranjang;
    private ProgressBar pbDataProdukKeranjang;

    private RecyclerView recyclerViewKeranjang;
    private List<DataKeranjang> dataProdukListKeranjang = new ArrayList<>();
    private AdapterKeranjangBelanja adapterKeranjangBelanja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_belanja);

        backKeranjang = findViewById(R.id.imgBackKeranjang);
        backKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        srlDataKeranjang = findViewById(R.id.sw_keranjangbelanja);
        pbDataProdukKeranjang = findViewById(R.id.pb_keranjangbelanja);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerViewKeranjang = (RecyclerView)findViewById(R.id.RecviewKeranjang);;
        recyclerViewKeranjang.setHasFixedSize(true);
        recyclerViewKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangBelanjaActivity.this, LinearLayoutManager.VERTICAL, false));

        srlDataKeranjang.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataKeranjang.setRefreshing(true);
                retrieveDataPembeli();
                srlDataKeranjang.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveDataPembeli();
    }

    public void retrieveDataPembeli(){
        pbDataProdukKeranjang.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataKeranjang = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataKeranjang> tampilDataKeranjang = requestDataKeranjang.RetrieveDataKeranjang();

        tampilDataKeranjang.enqueue(new Callback<ResponseDataKeranjang>() {
            @Override
            public void onResponse(Call<ResponseDataKeranjang> call, Response<ResponseDataKeranjang> response) {
                if (response.isSuccessful()){

                    //Toast.makeText(HalamanUtamaPembeliActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                    dataProdukListKeranjang = response.body().getData();

                    adapterKeranjangBelanja = new AdapterKeranjangBelanja(KeranjangBelanjaActivity.this, dataProdukListKeranjang);
                    recyclerViewKeranjang.setAdapter(adapterKeranjangBelanja);
                    adapterKeranjangBelanja.notifyDataSetChanged();


                }
                pbDataProdukKeranjang.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataKeranjang> call, Throwable t) {
                Toast.makeText(KeranjangBelanjaActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataProdukKeranjang.setVisibility(View.GONE);
            }
        });


    }
}