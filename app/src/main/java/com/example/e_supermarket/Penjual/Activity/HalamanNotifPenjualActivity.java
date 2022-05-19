package com.example.e_supermarket.Penjual.Activity;

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
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterBuatPesanan;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Adapter.AdapterPesananPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanNotifPenjualActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;

    private SwipeRefreshLayout srlDataProdukOrderanPenjual;
    private ProgressBar pbDataProdukOrderanPenjual;

    private RecyclerView recyclerViewOrderanPenjual;
    private List<BuatPesanan> buatPesananListPenjual = new ArrayList<>();
    private AdapterPesananPenjual adapterPesananPenjual;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanUtamaPenjualActivity.class));
                    break;
                case R.id.notifpenjual:
                    break;
                case R.id.chatpenjual:
                    startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanChatPenjualActivity.class));
                    break;
                case R.id.profilpenjual:
                    startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanProfilePenjualActivity.class));
                    break;
                case R.id.logoutpenjual:
                    firebaseAuth.signOut();
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
        setContentView(R.layout.activity_halaman_notif_penjual);
        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);
        firebaseAuth = FirebaseAuth.getInstance();

        srlDataProdukOrderanPenjual = findViewById(R.id.sw_dataprodukPesananPenjual);
        pbDataProdukOrderanPenjual = findViewById(R.id.pb_dataprodukPesananPenjual);

        recyclerViewOrderanPenjual = findViewById(R.id.recItemPesananPenjual);;
        recyclerViewOrderanPenjual.setHasFixedSize(true);
        recyclerViewOrderanPenjual.setLayoutManager(new LinearLayoutManager(HalamanNotifPenjualActivity.this, LinearLayoutManager.VERTICAL, false));

        srlDataProdukOrderanPenjual.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProdukOrderanPenjual.setRefreshing(true);
                retrieveDataOrderan();
                srlDataProdukOrderanPenjual.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveDataOrderan();
    }

    private void retrieveDataOrderan() {
        pbDataProdukOrderanPenjual.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataOrderan = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseBuatPesanan> tampilDataOrderan = requestDataOrderan.RetrieveDataOrderan();

        tampilDataOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
            @Override
            public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                if (response.isSuccessful()){
                    //int kode = response.body().getKode();
                    //String pesan = response.body().getPesan();

                    buatPesananListPenjual = response.body().getData();

                    adapterPesananPenjual = new AdapterPesananPenjual(HalamanNotifPenjualActivity.this, buatPesananListPenjual);
                    recyclerViewOrderanPenjual.setAdapter(adapterPesananPenjual);
                    adapterPesananPenjual.notifyDataSetChanged();
                }
                pbDataProdukOrderanPenjual.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                Toast.makeText(HalamanNotifPenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataProdukOrderanPenjual.setVisibility(View.GONE);
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
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanNotifPenjualActivity.this, MainActivity.class);
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