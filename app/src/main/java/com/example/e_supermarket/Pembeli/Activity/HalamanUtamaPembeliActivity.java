package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterItemPembeli;
import com.example.e_supermarket.Penjual.Activity.HalamanChatPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanNotifPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanUtamaPembeliActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPembeli;
    private SwipeRefreshLayout srlDataProduk;
    private ProgressBar pbDataProdukPembeli;

    private RecyclerView recyclerViewPembeli;
    private List<DataProduk> dataProdukListPembeli = new ArrayList<>();
    private AdapterItemPembeli adapterItemPembeli;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_pembeli = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepembeli:
                    //startActivity(new Intent());
                    break;
                case R.id.orderanpembeli:
                    startActivity(new Intent(HalamanUtamaPembeliActivity.this, OrderanPembeliActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpembeli:
                    startActivity(new Intent(HalamanUtamaPembeliActivity.this, ChatPembeliActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpembeli:
                    startActivity(new Intent(HalamanUtamaPembeliActivity.this, ProfilePembeliActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpembelil:
                    //Fp = new LogoutFragmentPenjual();
                    onBackPressed();
                    return true;

            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_penjual, Fp).commit();
            getContentTransitionManager();
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama_pembeli);
        srlDataProduk = findViewById(R.id.sw_dataprodukPembeli);
        pbDataProdukPembeli = findViewById(R.id.pb_dataprodukPembeli);

        bottomNavigationViewPembeli = findViewById(R.id.nav_pembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(navigation_pembeli);

        recyclerViewPembeli = (RecyclerView)findViewById(R.id.RecviewPembeli);;
        recyclerViewPembeli.setHasFixedSize(true);
        recyclerViewPembeli.setLayoutManager(new LinearLayoutManager(HalamanUtamaPembeliActivity.this, LinearLayoutManager.VERTICAL, false));

        srlDataProduk.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProduk.setRefreshing(true);
                retrieveDataPembeli();
                srlDataProduk.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveDataPembeli();
    }

    public void retrieveDataPembeli(){
        pbDataProdukPembeli.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataProdukPembeli = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataProduk> tampilDataPembeli = requestDataProdukPembeli.RetrieveDataProduk();

        tampilDataPembeli.enqueue(new Callback<ResponseDataProduk>() {
            @Override
            public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                if (response.isSuccessful()){
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    if (kode == 200){
                        Toast.makeText(HalamanUtamaPembeliActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                        dataProdukListPembeli = response.body().getData();

                        adapterItemPembeli = new AdapterItemPembeli(HalamanUtamaPembeliActivity.this, dataProdukListPembeli);
                        recyclerViewPembeli.setAdapter(adapterItemPembeli);
                        adapterItemPembeli.notifyDataSetChanged();
                    }

                }
                pbDataProdukPembeli.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                Toast.makeText(HalamanUtamaPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataProdukPembeli.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanUtamaPembeliActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}