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

import com.example.e_supermarket.Kurir.Adapter.AdapterProfileKurir;
import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanProfilKurirActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewKurir;
    private SwipeRefreshLayout srlProfKurir;
    private ProgressBar pbProfKurir;

    private RecyclerView recyclerViewK;
    private List<DataKurir> dataKurirList = new ArrayList<>();
    private AdapterProfileKurir adapterProfileKurir;

    FirebaseAuth firebaseAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_kurir = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homekurir:
                    startActivity(new Intent(HalamanProfilKurirActivity.this, HalamanUtamaKurirActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.profilkurir:
                    // startActivity(new Intent(ProfilePembeliActivity.this, ProfilePembeliActivity.class));
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
        setContentView(R.layout.activity_halaman_profil_kurir);
        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationViewKurir = findViewById(R.id.nav_kurir);
        bottomNavigationViewKurir.setOnNavigationItemSelectedListener(navigation_kurir);

        srlProfKurir = findViewById(R.id.sw_datakurir);
        pbProfKurir = findViewById(R.id.pb_dataKurir);

        recyclerViewK = findViewById(R.id.recProfileKurir);;
        recyclerViewK.setHasFixedSize(true);
        recyclerViewK.setLayoutManager(new LinearLayoutManager(HalamanProfilKurirActivity.this, LinearLayoutManager.VERTICAL, false));

        srlProfKurir.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlProfKurir.setRefreshing(true);
                getProfileKurir();
                srlProfKurir.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfileKurir();
    }

    private void getProfileKurir() {
        pbProfKurir.setVisibility(View.VISIBLE);
        ApiRequestDataKurir requestDataKurir = RetroServer.konekRetrofit().create(ApiRequestDataKurir.class);
        Call<ResponseDataKurir> tampilData = requestDataKurir.RetrieveDataKurir();

        tampilData.enqueue(new Callback<ResponseDataKurir>() {
            @Override
            public void onResponse(Call<ResponseDataKurir> call, Response<ResponseDataKurir> response) {
                if (response.isSuccessful()){
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    if (kode == 200){
                        //Toast.makeText(HalamanProfilKurirActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                        dataKurirList = response.body().getDataKurir();

                        adapterProfileKurir = new AdapterProfileKurir(HalamanProfilKurirActivity.this, dataKurirList);
                        recyclerViewK.setAdapter(adapterProfileKurir);
                        adapterProfileKurir.notifyDataSetChanged();
                    }

                }

                pbProfKurir.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataKurir> call, Throwable t) {
                Toast.makeText(HalamanProfilKurirActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbProfKurir.setVisibility(View.GONE);
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
                        Intent intent = new Intent(HalamanProfilKurirActivity.this, MainActivity.class);
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