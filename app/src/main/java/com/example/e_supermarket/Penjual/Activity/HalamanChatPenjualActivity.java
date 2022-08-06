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

import com.example.e_supermarket.AppConfig;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Adapter.AdapterDaftarChatPenjual;
import com.example.e_supermarket.Penjual.Adapter.AdapterKontakPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanChatPenjualActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;

    private SwipeRefreshLayout srlChatPenj;
    private ProgressBar pbChatPenj;

    private RecyclerView recyclerView;
    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    private AdapterDaftarChatPenjual adapterDaftarChatPenjual;

    AppConfig appConfig;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    startActivity(new Intent(HalamanChatPenjualActivity.this, HalamanUtamaPenjualActivity.class));
                    break;
                case R.id.notifpenjual:
                    startActivity(new Intent(HalamanChatPenjualActivity.this, HalamanNotifPenjualActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpenjual:
                    //startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanChatPenjualActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpenjual:
                    startActivity(new Intent(HalamanChatPenjualActivity.this, HalamanProfilePenjualActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpenjual:
                    //Fp = new LogoutFragmentPenjual();
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
        setContentView(R.layout.activity_halaman_chat_penjual);
        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);
        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton btnKontakChat = findViewById(R.id.fab_msgpenj);
        btnKontakChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanChatPenjualActivity.this, KontakChatPenjualActivity.class));
            }
        });

        srlChatPenj = findViewById(R.id.sw_chatPenj);
        pbChatPenj = findViewById(R.id.pb_halchatPemb);


        recyclerView = (RecyclerView)findViewById(R.id.recChatPenj);;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HalamanChatPenjualActivity.this, LinearLayoutManager.VERTICAL, false));

        srlChatPenj.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlChatPenj.setRefreshing(true);
                getProfilePembeli();
                srlChatPenj.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePembeli();
    }

    private void getProfilePembeli() {
        pbChatPenj.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataPembeli> tampilData = requestDataPembeli.RetrieveDataPembeli();

        tampilData.enqueue(new Callback<ResponseDataPembeli>() {
            @Override
            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                if (response.isSuccessful()){
                    dataPembeliList = response.body().getDataPembeli();

                    adapterDaftarChatPenjual = new AdapterDaftarChatPenjual(HalamanChatPenjualActivity.this, dataPembeliList);
                    recyclerView.setAdapter(adapterDaftarChatPenjual);
                    adapterDaftarChatPenjual.notifyDataSetChanged();

                }

                pbChatPenj.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                Toast.makeText(HalamanChatPenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbChatPenj.setVisibility(View.GONE);
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
                        appConfig.UpdateUserLoginStatus(false);
                        Intent intent = new Intent(HalamanChatPenjualActivity.this, MainActivity.class);
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