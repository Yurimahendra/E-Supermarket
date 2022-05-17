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
import com.example.e_supermarket.Pembeli.Adapter.AdapterBuatPesanan;
import com.example.e_supermarket.Pembeli.Adapter.AdapterItemPembeli;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderanPembeliActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPembeli;
    FirebaseAuth Fauth;

    private SwipeRefreshLayout srlDataProdukOrderan;
    private ProgressBar pbDataProdukOrderan;

    private RecyclerView recyclerViewOrderan;
    private List<BuatPesanan> buatPesananList = new ArrayList<>();
    private AdapterBuatPesanan adapterBuatPesanan;


    private BottomNavigationView.OnNavigationItemSelectedListener navigation_pembeli = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepembeli:
                    startActivity(new Intent(OrderanPembeliActivity.this, HalamanUtamaPembeliActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.orderanpembeli:
                    //startActivity(new Intent(OrderanPembeliActivity.this, OrderanPembeliActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpembeli:
                    startActivity(new Intent(OrderanPembeliActivity.this, ChatPembeliActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpembeli:
                    startActivity(new Intent(OrderanPembeliActivity.this, ProfilePembeliActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpembelil:
                    //Fp = new LogoutFragmentPenjual();
                    Fauth.signOut();
                    onBackPressedOut();
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
        setContentView(R.layout.activity_orderan_pembeli);
        bottomNavigationViewPembeli = findViewById(R.id.nav_pembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(navigation_pembeli);

        Fauth = FirebaseAuth.getInstance();

        srlDataProdukOrderan = findViewById(R.id.sw_dataprodukPesanan);
        pbDataProdukOrderan = findViewById(R.id.pb_dataprodukPesanan);

        recyclerViewOrderan = findViewById(R.id.recItemPesanan);;
        recyclerViewOrderan.setHasFixedSize(true);
        recyclerViewOrderan.setLayoutManager(new LinearLayoutManager(OrderanPembeliActivity.this, LinearLayoutManager.VERTICAL, false));

        srlDataProdukOrderan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProdukOrderan.setRefreshing(true);
                retrieveDataOrderan();
                srlDataProdukOrderan.setRefreshing(false);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        retrieveDataOrderan();
    }

    private void retrieveDataOrderan() {
        pbDataProdukOrderan.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataOrderan = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseBuatPesanan> tampilDataOrderan = requestDataOrderan.RetrieveDataOrderan();

        tampilDataOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
            @Override
            public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                if (response.isSuccessful()){
                    //int kode = response.body().getKode();
                    //String pesan = response.body().getPesan();

                    buatPesananList = response.body().getData();

                    adapterBuatPesanan = new AdapterBuatPesanan(OrderanPembeliActivity.this, buatPesananList);
                    recyclerViewOrderan.setAdapter(adapterBuatPesanan);
                    adapterBuatPesanan.notifyDataSetChanged();
                }
                pbDataProdukOrderan.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                Toast.makeText(OrderanPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataProdukOrderan.setVisibility(View.GONE);
            }
        });
    }


    private void onBackPressedOut() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //firebaseAuth.signOut();
                        Intent intent = new Intent(OrderanPembeliActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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