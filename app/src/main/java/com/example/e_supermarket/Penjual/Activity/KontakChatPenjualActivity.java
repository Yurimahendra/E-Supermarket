package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Adapter.AdapterKontakPenjual;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KontakChatPenjualActivity extends AppCompatActivity {
    ImageView btnBackKntkPnj;
    private SwipeRefreshLayout srlKontakPenj;
    private ProgressBar pbkontakPenj;

    private RecyclerView recyclerView;
    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    private AdapterKontakPenjual adapterKontakPenjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_chat_penjual);
        srlKontakPenj = findViewById(R.id.sw_KontakPenj);
        pbkontakPenj = findViewById(R.id.pb_kontakPenj);


        recyclerView = (RecyclerView)findViewById(R.id.recKontakPenj);;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(KontakChatPenjualActivity.this, LinearLayoutManager.VERTICAL, false));

        btnBackKntkPnj = findViewById(R.id.imgBackKontakPenj);
        btnBackKntkPnj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        srlKontakPenj.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlKontakPenj.setRefreshing(true);
                getProfilePembeli();
                srlKontakPenj.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePembeli();
    }

    private void getProfilePembeli() {
        pbkontakPenj.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataPembeli> tampilData = requestDataPembeli.RetrieveDataPembeli();

        tampilData.enqueue(new Callback<ResponseDataPembeli>() {
            @Override
            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                if (response.isSuccessful()){
                    dataPembeliList = response.body().getDataPembeli();

                    adapterKontakPenjual = new AdapterKontakPenjual(KontakChatPenjualActivity.this, dataPembeliList);
                    recyclerView.setAdapter(adapterKontakPenjual);
                    adapterKontakPenjual.notifyDataSetChanged();

                }

                pbkontakPenj.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                Toast.makeText(KontakChatPenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbkontakPenj.setVisibility(View.GONE);
            }
        });


    }
}