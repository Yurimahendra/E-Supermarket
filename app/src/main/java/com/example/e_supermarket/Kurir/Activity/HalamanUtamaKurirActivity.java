package com.example.e_supermarket.Kurir.Activity;

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

import com.example.e_supermarket.Kurir.Adapter.AdapterPesananKurir;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Activity.ChatPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Activity.HalamanNotifPenjualActivity;
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

public class HalamanUtamaKurirActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewKurir;
    FirebaseAuth firebaseAuth;

    private SwipeRefreshLayout srlDataProdukOrderanKurir;
    private ProgressBar pbDataProdukOrderanKurir;

    private RecyclerView recyclerViewOrderanKurir;
    private List<BuatPesanan> buatPesananListKurir = new ArrayList<>();
    private AdapterPesananKurir adapterPesananKurir;


    private BottomNavigationView.OnNavigationItemSelectedListener navigation_Kurir = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homekurir:
                    //startActivity(new Intent());
                    break;
                case R.id.profilkurir:
                    startActivity(new Intent(HalamanUtamaKurirActivity.this, HalamanProfilKurirActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutkurir:
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
        setContentView(R.layout.activity_halaman_utama_kurir);
       /* srlDataPesanan = findViewById(R.id.sw_data);
        pbDataPesanan = findViewById(R.id.pb_dataprodukPembeli);*/

        bottomNavigationViewKurir = findViewById(R.id.nav_kurir);
        bottomNavigationViewKurir.setOnNavigationItemSelectedListener(navigation_Kurir);

        firebaseAuth = FirebaseAuth.getInstance();

        srlDataProdukOrderanKurir = findViewById(R.id.sw_dataprodukPesananKurir);
        pbDataProdukOrderanKurir = findViewById(R.id.pb_dataprodukPesananKurir);

        recyclerViewOrderanKurir = findViewById(R.id.recItemPesananKurir);;
        recyclerViewOrderanKurir.setHasFixedSize(true);
        recyclerViewOrderanKurir.setLayoutManager(new LinearLayoutManager(HalamanUtamaKurirActivity.this, LinearLayoutManager.VERTICAL, false));

        srlDataProdukOrderanKurir.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProdukOrderanKurir.setRefreshing(true);
                retrieveDataOrderan();
                srlDataProdukOrderanKurir.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveDataOrderan();
    }

    private void retrieveDataOrderan() {
        pbDataProdukOrderanKurir.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataOrderan = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseBuatPesanan> tampilDataOrderan = requestDataOrderan.RetrieveDataOrderan();

        tampilDataOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
            @Override
            public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                if (response.isSuccessful()){
                    //int kode = response.body().getKode();
                    //String pesan = response.body().getPesan();

                    buatPesananListKurir = response.body().getData();

                    adapterPesananKurir = new AdapterPesananKurir(HalamanUtamaKurirActivity.this, buatPesananListKurir);
                    recyclerViewOrderanKurir.setAdapter(adapterPesananKurir);
                    adapterPesananKurir.notifyDataSetChanged();
                }
                pbDataProdukOrderanKurir.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                Toast.makeText(HalamanUtamaKurirActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataProdukOrderanKurir.setVisibility(View.GONE);
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
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanUtamaKurirActivity.this, MainActivity.class);
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